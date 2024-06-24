package com.gnivc.driverservice.repository;

import com.gnivc.driverservice.model.transport.Transport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TransportRepository extends JpaRepository<Transport, UUID> {
}
