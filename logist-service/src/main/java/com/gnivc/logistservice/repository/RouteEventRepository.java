package com.gnivc.logistservice.repository;

import com.gnivc.logistservice.model.route.RouteEvent;
import com.gnivc.logistservice.model.route.stat.RouteEventCount;
import com.gnivc.model.RouteEventType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public interface RouteEventRepository extends JpaRepository<RouteEvent, UUID> {

    @Query(value = "SELECT new com.gnivc.logistservice.model.route.stat.RouteEventCount(COUNT(*), re.eventType) " +
        "FROM RouteEvent re " +
        "WHERE re.company.id = ?1 " +
        "AND re.eventType IN (?3) " +
        "AND re.timestamp >= ?2 " +
        "GROUP BY re.eventType")
    List<RouteEventCount> getRouteEventCounts(UUID companyId, Instant from, List<RouteEventType> eventTypes);
}
