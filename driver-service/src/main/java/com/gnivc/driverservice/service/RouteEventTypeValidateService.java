package com.gnivc.driverservice.service;

import com.gnivc.driverservice.model.route.RouteEvent;
import com.gnivc.driverservice.model.route.RouteEventRequest;
import com.gnivc.model.RouteEventType;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class RouteEventTypeValidateService {

    public boolean isRouteEventTypeCorrect(RouteEventRequest routeEventRequest, RouteEvent routeEvent) {
        return switch (routeEvent.getEventType()) {
            case CREATED -> processCreatedRouteEvent(routeEventRequest);
            case STARTED -> processStartedRouteEvent(routeEventRequest);
            case CRASH -> processCrashRouteEvent(routeEventRequest);
            case FAILURE -> processFailureRouteEvent(routeEventRequest);
            case ENDED -> processEndedRouteEvent(routeEventRequest);
            case CANCELED -> processCanceledRouteEvent(routeEventRequest);
            default -> throw new RuntimeException("Invalid RouteEventType: " + routeEvent.getEventType());
        };
    }

    private boolean processCreatedRouteEvent(RouteEventRequest routeEventRequest) {
        final RouteEventType[] types = new RouteEventType[]{
            RouteEventType.CANCELED,
            RouteEventType.CRASH,
            RouteEventType.FAILURE,
            RouteEventType.STARTED
        };

        return Arrays.stream(types)
            .anyMatch(type ->
                type.equals(routeEventRequest.getEventType()));
    }

    private boolean processStartedRouteEvent(RouteEventRequest routeEventRequest) {
        final RouteEventType[] types = new RouteEventType[]{
            RouteEventType.CANCELED,
            RouteEventType.CRASH,
            RouteEventType.FAILURE,
            RouteEventType.ENDED,
        };

        return Arrays.stream(types)
            .anyMatch(type ->
                type.equals(routeEventRequest.getEventType()));
    }

    private boolean processCrashRouteEvent(RouteEventRequest routeEventRequest) {
        final RouteEventType[] types = new RouteEventType[]{
            RouteEventType.CANCELED,
            RouteEventType.STARTED,
        };

        return Arrays.stream(types)
            .anyMatch(type ->
                type.equals(routeEventRequest.getEventType()));
    }

    private boolean processFailureRouteEvent(RouteEventRequest routeEventRequest) {
        final RouteEventType[] types = new RouteEventType[]{
            RouteEventType.CANCELED,
            RouteEventType.STARTED,
        };

        return Arrays.stream(types)
            .anyMatch(type ->
                type.equals(routeEventRequest.getEventType()));
    }

    private boolean processCanceledRouteEvent(RouteEventRequest routeEventRequest) {
        return false;
    }

    private boolean processEndedRouteEvent(RouteEventRequest routeEventRequest) {
        return false;
    }
}
