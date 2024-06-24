package com.gnivc.logistservice.service;

import com.gnivc.commonexception.exception.NotFoundException;
import com.gnivc.logistservice.PageableGenerator;
import com.gnivc.logistservice.mapper.TaskMapper;
import com.gnivc.logistservice.model.cargo.Cargo;
import com.gnivc.logistservice.model.cargo.TaskCargo;
import com.gnivc.logistservice.model.driver.Driver;
import com.gnivc.logistservice.model.task.Task;
import com.gnivc.logistservice.model.task.TaskCreateRequest;
import com.gnivc.logistservice.model.task.TaskInfo;
import com.gnivc.logistservice.model.transport.Transport;
import com.gnivc.logistservice.repository.CargoRepository;
import com.gnivc.logistservice.repository.TaskCargoRepository;
import com.gnivc.logistservice.repository.TaskRepository;
import com.gnivc.model.Point;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final TransportService transportService;
    private final TaskMapper taskMapper;
    private final DriverService driverService;
    private final CargoRepository cargoRepository;
    private final TaskCargoRepository taskCargoRepository;
    private final KafkaTemplate<String, com.gnivc.model.TaskInfo> taskKafkaTemplate;
    @Value("${spring.kafka.topic.task.name}")
    private String taskTopic;

    @Transactional
    public TaskInfo addTask(TaskCreateRequest taskCreateRequest, UUID companyId) {
        UUID driverId = taskCreateRequest.getDriver();
        Driver driver = driverService.getDriver(driverId);
        Transport transport = getTransport(taskCreateRequest, companyId);
        Task task = taskMapper.toTask(taskCreateRequest);
        getCargos(taskCreateRequest);
        task.setTransport(transport);
        task.setDriver(driver);
        taskRepository.saveAndFlush(task);

        List<TaskCargo> taskCargos = taskCreateRequest.getCargoDetails().stream()
            .map(cargo ->
                new TaskCargo(task.getId(), cargo.getId(), cargo.getCount()))
            .toList();

        taskCargoRepository.saveAllAndFlush(taskCargos);
        sendKafka(task, taskCargos);
        return taskMapper.toTaskInfo(task);
    }

    public List<TaskInfo> getTasksInfo(UUID companyId, int from, int size) {
        List<Task> tasks = taskRepository.getTasksByTransportCompanyId(companyId, PageableGenerator.getPageable(from, size));
        return taskMapper.toTaskInfoList(tasks);

    }

    public TaskInfo getTaskInfo(UUID taskId) {
        Task task = getTask(taskId);
        return taskMapper.toTaskInfo(task);
    }

    public Task getTask(UUID taskId) {
        return taskRepository.findById(taskId)
            .orElseThrow(() -> new NotFoundException(Task.class, taskId));
    }

    private Transport getTransport(TaskCreateRequest taskCreateRequest, UUID companyId) {
        UUID transportId = taskCreateRequest.getTransport();
        Transport transport = transportService.getTransport(transportId);
        if (!transport.getCompany().getId().equals(companyId)) {
            throw new RuntimeException();
        }
        return transport;
    }

    private List<Cargo> getCargos(TaskCreateRequest taskCreateRequest) {
        List<UUID> cargosId = taskCreateRequest.getCargoDetails().stream()
            .map(TaskCreateRequest.CargoDetails::getId)
            .toList();
        List<Cargo> cargos = cargoRepository.findAllById(cargosId);
        if (!Objects.equals(cargos.size(), cargosId.size())) {
            throw new RuntimeException();
        }
        return cargos;
    }

    private void sendKafka(Task task, List<TaskCargo> taskCargos) {
        com.gnivc.model.TaskInfo taskInfo = com.gnivc.model.TaskInfo.builder()
            .id(task.getId())
            .transport(task.getTransport().getId())
            .driver(task.getDriver().getId())
            .locationStart(new Point(task.getLocationStartLon(), task.getLocationStartLat()))
            .locationEnd(new Point(task.getLocationEndLon(), task.getLocationEndLat()))
            .cargos(taskCargos.stream()
                .map(cargo ->
                    com.gnivc.model.TaskInfo.CargoDetails.builder()
                        .cargoId(cargo.getId()
                            .getCargoId())
                        .count(cargo.getCount())
                        .build())
                .toList())
            .build();
        taskKafkaTemplate.send(taskTopic, taskInfo);
    }
}
