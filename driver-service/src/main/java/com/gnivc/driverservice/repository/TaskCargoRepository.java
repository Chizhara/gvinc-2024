package com.gnivc.driverservice.repository;

import com.gnivc.driverservice.model.cargo.TaskCargo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskCargoRepository extends JpaRepository<TaskCargo, Long> {
}
