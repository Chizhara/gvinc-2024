package com.gnivc.driverservice.repository;

import com.gnivc.driverservice.model.cargo.Cargo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CargoRepository extends JpaRepository<Cargo, UUID> {
}
