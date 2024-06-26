package com.gnivc.dwhservice.client;

import com.gnivc.dwhservice.model.RouteEventCount;
import com.gnivc.dwhservice.model.TaskStatInfo;
import com.gnivc.model.RouteEventType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Component
public class LogistClient {
    private final WebClient webClient;

    public LogistClient() {
        this.webClient = WebClient.builder()
            .baseUrl("http://localhost:8083")
            .build();
    }

    public List<RouteEventCount> routeEventCounts(UUID companyId, List<RouteEventType> routeEventTypes) {
        final String endpoint = "/stat/company/{companyId}/route/event/type/count";

        String url = UriComponentsBuilder.fromPath(endpoint)
            .uriVariables(Map.of("companyId", companyId))
            .queryParam("eventType", routeEventTypes).build().toUriString();

        return webClient.get()
            .uri(url)
            .retrieve()
            .toEntityList(RouteEventCount.class).block().getBody();
    }

    public TaskStatInfo getTaskStat(UUID companyId) {
        final String endpoint = "/stat/company/{companyId}/task";

        String url = UriComponentsBuilder.fromPath(endpoint)
            .uriVariables(Map.of("companyId", companyId))
            .build().toUriString();

        return webClient.get()
            .uri(url)
            .retrieve()
            .toEntity(TaskStatInfo.class).block()
            .getBody();
    }
}
