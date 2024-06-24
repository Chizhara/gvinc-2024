package com.gnivc.logistservice.service;

import com.gnivc.logistservice.mapper.RouteMapper;
import com.gnivc.logistservice.model.route.RouteEvent;
import com.gnivc.logistservice.model.task.Task;
import com.gnivc.logistservice.repository.RouteEventRepository;
import com.gnivc.model.RouteEventInfo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RouteEventService {
    private final RouteEventRepository routeEventRepository;
    private final RouteMapper routeMapper;
    private final TaskService taskService;

    @Transactional
    public void addRouteEvent(RouteEventInfo routeEventInfo) {
        RouteEvent routeEvent = routeMapper.toRouteEvent(routeEventInfo);
        Task task = taskService.getTask(routeEventInfo.getTaskId());
        routeEvent.setTask(task);
        routeEventRepository.save(routeEvent);
    }
}
