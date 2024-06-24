package com.gnivc.driverservice.model.route;

import com.gnivc.model.Point;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoutePointRequest {
    private Point point;
}
