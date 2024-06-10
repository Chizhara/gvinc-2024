package com.gnivc.portalservice.repository;

import com.gnivc.portalservice.model.transport.Transport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TransportRepository extends JpaRepository<Transport, UUID> {
}