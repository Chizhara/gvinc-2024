package com.gnivc.driverservice.model.route;

import com.gnivc.model.Point;
import lombok.Data;

import java.util.UUID;

@Data
public class RoutePointInfoResponse {
    private UUID id;
    private UUID routeId;
    private Point point;
}
