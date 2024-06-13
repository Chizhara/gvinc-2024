package com.gnivc.logistservice.repository;

import com.gnivc.logistservice.model.Transport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TransportRepository extends JpaRepository<Transport, UUID> {
}
