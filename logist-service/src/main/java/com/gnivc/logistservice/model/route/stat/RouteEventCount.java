package com.gnivc.logistservice.model.route.stat;

import com.gnivc.model.RouteEventType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RouteEventCount {
    long count;
    RouteEventType routeEventType;
}
