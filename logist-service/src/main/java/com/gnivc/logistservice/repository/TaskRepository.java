package com.gnivc.logistservice.repository;

import com.gnivc.logistservice.model.task.Task;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TaskRepository extends JpaRepository<Task, UUID> {

    List<Task> getTasksByTransportCompanyId(UUID companyId, Pageable pageable);
}
