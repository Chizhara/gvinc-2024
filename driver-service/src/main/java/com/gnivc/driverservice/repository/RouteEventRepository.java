package com.gnivc.driverservice.repository;

import com.gnivc.driverservice.model.route.RouteEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RouteEventRepository extends JpaRepository<RouteEvent, UUID> {
    @Query("SELECT RouteEvent " +
        "FROM RouteEvent " +
        "WHERE id = ?1 AND time = " +
        "(SELECT MAX(reTemp.time) FROM RouteEvent reTemp WHERE reTemp.route.id = ?1)")
    Optional<RouteEvent> findLatestEventByRouteId(UUID routeId);

    @Query(value = "SELECT re.id, " +
        "       re.route_id, " +
        "       re.time, " +
        "       re.type " +
        "FROM routes_events re " +
        "JOIN ( " +
        "    SELECT type AS sub_type, MAX(time) AS max_time " +
        "    FROM routes_events r " +
        "    WHERE r.route_id = ?1 " +
        "    GROUP BY type " +
        ") sub ON re.type = sub.sub_type AND re.time = sub.max_time " +
        "WHERE re.route_id = ?1", nativeQuery = true)
    List<RouteEvent> findDistinctEventsByRouteId(UUID routeId);
}
