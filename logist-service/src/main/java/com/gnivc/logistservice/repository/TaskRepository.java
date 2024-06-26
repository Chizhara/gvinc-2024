package com.gnivc.logistservice.repository;

import com.gnivc.logistservice.model.task.Task;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public interface TaskRepository extends JpaRepository<Task, UUID> {

    List<Task> getTasksByTransportCompanyId(UUID companyId, Pageable pageable);

    @Query("SELECT COUNT(*) FROM Task WHERE createTime >= ?2 AND company.id = ?1 ")
    Integer getTasksByTransportCompanyId(UUID companyId, Instant createTime);
}
