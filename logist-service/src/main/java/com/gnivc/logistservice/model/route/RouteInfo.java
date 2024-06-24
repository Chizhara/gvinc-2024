package com.gnivc.logistservice.model.route;

import com.gnivc.logistservice.model.task.Task;
import lombok.Data;

import java.security.Timestamp;
import java.util.UUID;

@Data
public class RouteInfo {
    private UUID id;
    private Timestamp startTime;
    private Timestamp createTime;
    private Timestamp endTime;
    private Task task;
}
