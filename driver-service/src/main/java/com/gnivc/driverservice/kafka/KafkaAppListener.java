package com.gnivc.driverservice.kafka;

import com.gnivc.driverservice.service.CargoService;
import com.gnivc.driverservice.service.TaskService;
import com.gnivc.driverservice.service.TransportService;
import com.gnivc.model.CargoInfo;
import com.gnivc.model.TaskInfo;
import com.gnivc.model.TransportInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaAppListener {
    private final TaskService taskService;
    private final TransportService transportService;
    private final CargoService cargoService;

    @KafkaListener(topics = "portal.transport.info", groupId = "driver",
        containerFactory = "transportKafkaListenerContainerFactory")
    public void listenTransport(TransportInfo transportInfo) {
        transportService.addTransport(transportInfo);
    }

    @KafkaListener(topics = {"logist.task.info", "driver.task.info.buf"}, groupId = "driver",
        containerFactory = "taskKafkaListenerContainerFactory")
    public void listenTasks(TaskInfo taskInfo) {
        taskService.addTask(taskInfo);
    }

    @KafkaListener(topics = "logist.cargo.info", groupId = "driver",
        containerFactory = "cargoKafkaListenerContainerFactory")
    public void listenCargos(CargoInfo cargoInfo) {
        cargoService.addCargo(cargoInfo);
    }

}
