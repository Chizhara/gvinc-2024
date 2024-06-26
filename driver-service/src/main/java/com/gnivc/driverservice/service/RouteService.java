package com.gnivc.driverservice.service;

import com.gnivc.commonexception.exception.ForbiddenAccessException;
import com.gnivc.commonexception.exception.InvalidValueException;
import com.gnivc.commonexception.exception.NotFoundException;
import com.gnivc.driverservice.mapper.RouteMapper;
import com.gnivc.driverservice.model.route.Route;
import com.gnivc.driverservice.model.route.RouteEvent;
import com.gnivc.driverservice.model.route.RouteEventRequest;
import com.gnivc.driverservice.model.route.RouteInfoResponse;
import com.gnivc.driverservice.model.route.RoutePoint;
import com.gnivc.driverservice.model.route.RoutePointInfoResponse;
import com.gnivc.driverservice.model.route.RoutePointRequest;
import com.gnivc.driverservice.model.task.Task;
import com.gnivc.driverservice.repository.RouteEventRepository;
import com.gnivc.driverservice.repository.RoutePointRepository;
import com.gnivc.driverservice.repository.RouteRepository;
import com.gnivc.model.RouteEventInfo;
import com.gnivc.model.RouteEventType;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RouteService {
    private final RouteEventRepository routeEventRepository;
    private final RouteRepository routeRepository;
    private final TaskService taskService;
    private final RouteEventTypeValidateService routeEventTypeValidateService;
    private final KafkaTemplate<String, RouteEventInfo> routeEventKafkaTemplate;
    private final RouteMapper routeMapper;
    private final RoutePointRepository repository;
    private final RoutePointRepository routePointRepository;
    @Value("${spring.kafka.topic.route.event.name}")
    private String routeTopic;

    @Transactional
    public RouteInfoResponse createRoute(UUID driverId, UUID taskId, UUID companyId) {
        Route route = new Route();
        Task task = taskService.getTask(taskId);
        if (!task.getDriverId().equals(driverId)) {
            throw new ForbiddenAccessException(driverId, Task.class, taskId);
        }
        route.setTask(task);
        routeRepository.saveAndFlush(route);
        RouteEvent routeEvent = createRouteEvent(route, RouteEventType.CREATED);
        routeEventRepository.saveAndFlush(routeEvent);
        sendRouteEventInfo(routeEvent, companyId);
        return getRouteInfo(route.getId());
    }

    @Transactional
    public RouteInfoResponse addRouteEvent(RouteEventRequest routeEventRequest,
                                           UUID routeId,
                                           UUID driverId,
                                           UUID companyId) {
        Route route = getRoute(routeId);
        if (!route.getTask().getDriverId().equals(driverId)) {
            throw new ForbiddenAccessException(driverId, Route.class, routeId);
        }
        validateRouteEventType(routeEventRequest, routeId);
        RouteEvent routeEvent = createRouteEvent(route, routeEventRequest);
        routeEventRepository.saveAndFlush(routeEvent);
        sendRouteEventInfo(routeEvent, companyId);
        return getRouteInfo(routeId);
    }

    @Transactional
    public RoutePointInfoResponse addRoutePoint(RoutePointRequest routePointRequest, UUID routeId, UUID driverId) {
        Route route = getRoute(routeId);
        if (!route.getTask().getDriverId().equals(driverId)) {
            throw new ForbiddenAccessException(driverId, Route.class, routeId);
        }

        RoutePoint routePoint = routeMapper.toRoutePoint(routePointRequest);
        routePointRepository.save(routePoint);
        return routeMapper.toRoutePointInfoResponse(routePoint);
    }

    public RouteInfoResponse getRouteInfo(UUID routeId) {
        RouteInfoResponse.RouteInfoResponseBuilder routeInfoResponseBuilder = RouteInfoResponse.builder();
        List<RouteEvent> routeEvents = routeEventRepository.findDistinctEventsByRouteId(routeId);
        for (RouteEvent routeEvent : routeEvents) {
            switch (routeEvent.getEventType()) {
                case CREATED -> routeInfoResponseBuilder.createTime(routeEvent.getTime());
                case STARTED -> routeInfoResponseBuilder.startTime(routeEvent.getTime());
                case ENDED -> routeInfoResponseBuilder.endTime(routeEvent.getTime());
            }
        }

        RouteEvent lastEvent = routeEvents.get(routeEvents.size() - 1);
        return routeInfoResponseBuilder
            .lastEvent(RouteInfoResponse.RouteEventResponse.builder()
                .eventId(lastEvent.getId())
                .eventTime(lastEvent.getTime())
                .eventType(lastEvent.getEventType())
                .build())
            .build();
    }

    public RouteEvent getLastRouteEvent(UUID routeId) {
        return routeEventRepository.findLatestEventByRouteId(routeId)
            .orElseThrow(() -> new NotFoundException(RouteEvent.class, routeId));
    }

    private void validateRouteEventType(RouteEventRequest routeEventRequest, UUID routeId) {
        RouteEvent lastRouteEvent = getLastRouteEvent(routeId);
        boolean validate = routeEventTypeValidateService.isRouteEventTypeCorrect(routeEventRequest, lastRouteEvent);
        if (!validate) {
            throw new InvalidValueException("EventType", routeEventRequest.getEventType());
        }
    }


    public Route getRoute(UUID routeId) {
        return routeRepository.findById(routeId)
            .orElseThrow(() -> new NotFoundException(Route.class, routeId));
    }

    private RouteEvent createRouteEvent(Route route, RouteEventRequest routeEventRequest) {
        return RouteEvent.builder()
            .route(route)
            .eventType(routeEventRequest.getEventType())
            .time(Instant.now())
            .build();
    }

    private RouteEvent createRouteEvent(Route route, RouteEventType routeEventType) {
        return RouteEvent.builder()
            .route(route)
            .eventType(routeEventType)
            .time(Instant.now())
            .build();
    }

    private void sendRouteEventInfo(RouteEvent routeEvent, UUID companyId) {
        RouteEventInfo routeEventInfo = routeMapper.toRouteEventInfo(routeEvent);
        routeEventInfo.setCompanyId(companyId);
        routeEventKafkaTemplate.send(routeTopic, routeEventInfo);
    }
}
