package com.gnivc.logistservice.mapper;

import com.gnivc.logistservice.model.route.RouteEvent;
import com.gnivc.model.RouteEventInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface RouteMapper {
    @Mapping(target = "task", ignore = true)
    @Mapping(target = "timestamp", source = "eventTime")
    RouteEvent toRouteEvent(RouteEventInfo routeEventInfo);
}
