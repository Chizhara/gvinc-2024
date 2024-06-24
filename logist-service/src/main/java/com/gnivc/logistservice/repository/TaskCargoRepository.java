package com.gnivc.logistservice.repository;

import com.gnivc.logistservice.model.cargo.TaskCargo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TaskCargoRepository extends JpaRepository<TaskCargo, UUID> {
}
