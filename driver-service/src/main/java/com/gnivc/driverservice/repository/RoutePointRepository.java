package com.gnivc.driverservice.repository;

import com.gnivc.driverservice.model.route.RoutePoint;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoutePointRepository extends JpaRepository<RoutePoint, Long> {
}
