package com.gnivc.driverservice.model.route;

import com.gnivc.model.RouteEventType;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
public class RouteInfoResponse {
    private UUID id;
    private Instant startTime;
    private Instant createTime;
    private Instant endTime;
    private UUID taskId;
    private RouteEventResponse lastEvent;

    @Data
    @Builder
    public static class RouteEventResponse {
        private UUID eventId;
        private Instant eventTime;
        private RouteEventType eventType;
    }
}
