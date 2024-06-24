package com.gnivc.driverservice.repository;

import com.gnivc.driverservice.model.route.Route;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RouteRepository extends JpaRepository<Route, UUID> {

}
