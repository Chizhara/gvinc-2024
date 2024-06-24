package com.gnivc.driverservice.service;

import com.gnivc.commonexception.exception.NotFoundException;
import com.gnivc.driverservice.PageableGenerator;
import com.gnivc.driverservice.mapper.TaskMapper;
import com.gnivc.driverservice.model.cargo.Cargo;
import com.gnivc.driverservice.model.cargo.TaskCargo;
import com.gnivc.driverservice.model.task.Task;
import com.gnivc.driverservice.model.task.TaskInfoResponse;
import com.gnivc.driverservice.model.transport.Transport;
import com.gnivc.driverservice.repository.CargoRepository;
import com.gnivc.driverservice.repository.TaskCargoRepository;
import com.gnivc.driverservice.repository.TaskRepository;
import com.gnivc.driverservice.repository.TransportRepository;
import com.gnivc.model.TaskInfo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private final KafkaTemplate<String, TaskInfo> taskKafkaTemplate;
    private final TransportRepository transportRepository;
    private final CargoRepository cargoRepository;
    private final TaskCargoRepository taskCargoRepository;
    private final TransportService transportService;
    @Value("${spring.kafka.topic.task.buffer.name}")
    private String taskTopic;

    @Transactional
    public void addTask(TaskInfo taskInfo) {
        Optional<Transport> transportOpt = transportRepository.findById(taskInfo.getTransport());
        List<UUID> cargoIds = taskInfo.getCargos().stream().map(TaskInfo.CargoDetails::getCargoId).toList();
        List<Cargo> cargos = cargoRepository.findAllById(cargoIds);
        if (transportOpt.isEmpty() || !Objects.equals(cargos.size(), cargoIds.size())) {
            taskKafkaTemplate.send(taskTopic, taskInfo);
            return;
        }
        Task task = taskMapper.toTask(taskInfo);
        Transport transport = transportOpt.get();
        task.setTransport(transport);
        taskRepository.saveAndFlush(task);
        List<TaskCargo> taskCargos = taskInfo.getCargos().stream()
            .map(cargo ->
                new TaskCargo(task.getId(), cargo.getCargoId(), cargo.getCount()))
            .toList();

        taskCargoRepository.saveAll(taskCargos);
    }

    public Task getTask(UUID taskId) {
        return taskRepository.findById(taskId)
            .orElseThrow(() -> new NotFoundException(Task.class, taskId));
    }

    public List<TaskInfoResponse> getTasks(UUID driverId, int from, int size) {
        List<Task> tasks = taskRepository
            .findByDriverId(driverId, PageableGenerator.getPageable(from, size));
        return taskMapper.toTaskInfoResponse(tasks);
    }
}
