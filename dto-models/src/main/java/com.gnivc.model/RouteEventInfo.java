package com.gnivc.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RouteEventInfo {
    private UUID Id;
    private UUID routeId;
    private UUID taskId;
    private RouteEventType eventType;
    private Instant eventTime;
}
