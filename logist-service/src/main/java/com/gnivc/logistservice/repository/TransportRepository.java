package com.gnivc.logistservice.repository;

import com.gnivc.logistservice.model.transport.Transport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TransportRepository extends JpaRepository<Transport, UUID> {
}
