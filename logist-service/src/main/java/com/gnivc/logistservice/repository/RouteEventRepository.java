package com.gnivc.logistservice.repository;

import com.gnivc.logistservice.model.route.RouteEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RouteEventRepository extends JpaRepository<RouteEvent, UUID> {
}
