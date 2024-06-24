package com.gnivc.logistservice.mapper;

import com.gnivc.logistservice.model.cargo.TaskCargo;
import com.gnivc.logistservice.model.task.Task;
import com.gnivc.logistservice.model.task.TaskCreateRequest;
import com.gnivc.logistservice.model.task.TaskInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface TaskMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "driver", ignore = true)
    @Mapping(target = "transport", ignore = true)
    @Mapping(target = "locationStartLat", source = "locationStart.lat")
    @Mapping(target = "locationStartLon", source = "locationStart.lon")
    @Mapping(target = "locationEndLat", source = "locationEnd.lat")
    @Mapping(target = "locationEndLon", source = "locationEnd.lon")
    @Mapping(target = "cargos", ignore = true)
    Task toTask(TaskCreateRequest taskCreateRequest);

    @Mapping(target = "startPoint.lat", source = "locationStartLat")
    @Mapping(target = "startPoint.lon", source = "locationStartLon")
    @Mapping(target = "endPoint.lat", source = "locationEndLat")
    @Mapping(target = "endPoint.lon", source = "locationEndLon")
    TaskInfo toTaskInfo(Task task);

    @Mapping(target = "id", source = "cargo.id")
    @Mapping(target = "name", source = "cargo.name")
    TaskInfo.CargoDetails toTaskInfoCargoDetail(TaskCargo taskCargo);

    List<TaskInfo> toTaskInfoList(List<Task> tasks);
}
