package com.gnivc.logistservice.controller;

import com.gnivc.logistservice.model.route.stat.RouteEventCount;
import com.gnivc.logistservice.model.task.TaskStatInfo;
import com.gnivc.logistservice.service.StatService;
import com.gnivc.model.RouteEventType;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stat")
public class StatController {

    private final StatService statService;

    @GetMapping("/company/{companyId}/route/event/type/count")
    public List<RouteEventCount> getRouteEventStat(@PathVariable("companyId") UUID companyId,
                                                   @RequestParam("eventType") List<RouteEventType> routeEventType) {
        return statService.getRouteStat(companyId, routeEventType);
    }

    @GetMapping("/company/{companyId}/task")
    public TaskStatInfo getCreatedTasksCount(@PathVariable("companyId") UUID companyId) {
        return statService.getTaskStat(companyId);
    }
}
