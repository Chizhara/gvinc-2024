package com.gnivc.driverservice.mapper;

import com.gnivc.driverservice.model.cargo.TaskCargo;
import com.gnivc.driverservice.model.task.Task;
import com.gnivc.driverservice.model.task.TaskInfoResponse;
import com.gnivc.model.TaskInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(uses = {TransportMapper.class})
public interface TaskMapper {
    @Mapping(target = "driverId", source = "driver")
    @Mapping(target = "locationStartLat", source = "locationStart.lat")
    @Mapping(target = "locationStartLon", source = "locationStart.lon")
    @Mapping(target = "locationEndLat", source = "locationEnd.lat")
    @Mapping(target = "locationEndLon", source = "locationEnd.lon")
    @Mapping(target = "transport", ignore = true)
    @Mapping(target = "cargos", ignore = true)
    Task toTask(TaskInfo taskInfo);

    @Mapping(target = "startPoint.lat", source = "locationStartLat")
    @Mapping(target = "startPoint.lon", source = "locationStartLon")
    @Mapping(target = "endPoint.lat", source = "locationEndLat")
    @Mapping(target = "endPoint.lon", source = "locationEndLon")
    @Mapping(target = "cargoDetails", source = "cargos")
    TaskInfoResponse toTaskInfoResponse(Task task);

    @Mapping(target = "id", source = "cargo.id")
    @Mapping(target = "name", source = "cargo.name")
    TaskInfoResponse.CargoDetails toTaskInfoResponseCargoDetails(TaskCargo taskCargo);

    List<TaskInfoResponse> toTaskInfoResponse(List<Task> tasks);
}
