package com.gnivc.dwhservice.model;

import com.gnivc.model.RouteEventType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RouteEventCount {
    long count;
    RouteEventType routeEventType;
}
