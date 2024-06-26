package com.gnivc.dwhservice.service;

import com.gnivc.dwhservice.client.LogistClient;
import com.gnivc.dwhservice.model.RouteEventCount;
import com.gnivc.dwhservice.model.TaskStatInfo;
import com.gnivc.model.RouteEventType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StatService {

    private final LogistClient logistClient;

    public List<RouteEventCount> getTaskStat(UUID companyId, List<RouteEventType> routeEventTypes) {
        return logistClient.routeEventCounts(companyId, routeEventTypes);
    }

    public TaskStatInfo getTaskStat(UUID companyId) {
        return logistClient.getTaskStat(companyId);
    }
}
