package com.gnivc.logistservice.service;

import com.gnivc.logistservice.model.route.stat.RouteEventCount;
import com.gnivc.logistservice.model.task.TaskStatInfo;
import com.gnivc.logistservice.repository.RouteEventRepository;
import com.gnivc.logistservice.repository.TaskRepository;
import com.gnivc.model.RouteEventType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StatService {
    private final RouteEventRepository routeEventRepository;
    private final TaskRepository taskRepository;

    public List<RouteEventCount> getRouteStat(UUID companyId, List<RouteEventType> routeEventTypes) {
        LocalDateTime date = getToday();
        List<RouteEventCount> routeEventCounts = routeEventRepository
            .getRouteEventCounts(companyId, date.toInstant(ZoneOffset.ofHours(3)), routeEventTypes);
        return routeEventCounts;
    }

    public TaskStatInfo getTaskStat(UUID companyId) {
        LocalDateTime date = getToday();
        TaskStatInfo taskStatInfo = new TaskStatInfo();
        int count = taskRepository.getTasksByTransportCompanyId(companyId, date.toInstant(ZoneOffset.ofHours(3)));
        taskStatInfo.setCreatedTasks(count);
        return taskStatInfo;
    }


    private LocalDateTime getToday() {
        return LocalDateTime.of(LocalDate.now(), LocalTime.of(0, 0));
    }
}
