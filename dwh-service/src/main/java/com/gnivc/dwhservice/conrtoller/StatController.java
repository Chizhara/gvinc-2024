package com.gnivc.dwhservice.conrtoller;

import com.gnivc.dwhservice.model.RouteEventCount;
import com.gnivc.dwhservice.model.TaskStatInfo;
import com.gnivc.dwhservice.service.StatService;
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
@RequestMapping("/dwh/company/{companyId}")
public class StatController {

    private final StatService statService;

    @GetMapping("/route/event/type/count")
    public List<RouteEventCount> getRouteEventsCountByType(
        @PathVariable("companyId") UUID companyId,
        @RequestParam("eventType") List<RouteEventType> routeEventType) {
        return statService.getTaskStat(companyId, routeEventType);
    }

    @GetMapping("/task/stat")
    public TaskStatInfo getTaskStat(
        @PathVariable("companyId") UUID companyId) {
        return statService.getTaskStat(companyId);
    }

}
