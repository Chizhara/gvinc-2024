package com.gnivc.driverservice.repository;

import com.gnivc.driverservice.model.task.Task;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TaskRepository extends JpaRepository<Task, UUID> {
    List<Task> findByDriverId(UUID driverId, Pageable pageable);
}
