package com.gnivc.driverservice.model.route;

import com.gnivc.driverservice.validation.EnumFilter;
import com.gnivc.model.RouteEventType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RouteEventRequest {
    @EnumFilter(values = {"CREATED"}, exclude = true)
    private RouteEventType eventType;
}
