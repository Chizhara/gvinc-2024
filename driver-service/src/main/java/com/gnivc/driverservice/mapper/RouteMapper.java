package com.gnivc.driverservice.mapper;

import com.gnivc.driverservice.model.route.RouteEvent;
import com.gnivc.driverservice.model.route.RoutePoint;
import com.gnivc.driverservice.model.route.RoutePointInfoResponse;
import com.gnivc.driverservice.model.route.RoutePointRequest;
import com.gnivc.model.RouteEventInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = {TaskMapper.class})
public interface RouteMapper {
    @Mapping(target = "routeId", source = "route.id")
    @Mapping(target = "eventTime", source = "time")
    @Mapping(target = "taskId", source = "route.task.id")
    @Mapping(target = "companyId", ignore = true)
    RouteEventInfo toRouteEventInfo(RouteEvent routeEvent);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "lat", source = "point.lat")
    @Mapping(target = "lon", source = "point.lon")
    @Mapping(target = "route", ignore = true)
    RoutePoint toRoutePoint(RoutePointRequest routePointRequest);
    @Mapping(target = "routeId", source = "route.id")
    @Mapping(target = "point.lat", source = "lat")
    @Mapping(target = "point.lon", source = "lon")
    RoutePointInfoResponse toRoutePointInfoResponse(RoutePoint routePoint);
}
