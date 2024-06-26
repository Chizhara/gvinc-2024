package com.gnivc.logistservice.repository;

import com.gnivc.logistservice.model.cargo.Cargo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CargoRepository extends JpaRepository<Cargo, UUID> {
}
