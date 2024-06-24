package com.gnivc.driverservice.service;

import com.gnivc.commonexception.exception.NotFoundException;
import com.gnivc.driverservice.mapper.TransportMapper;
import com.gnivc.driverservice.model.transport.Transport;
import com.gnivc.driverservice.repository.TransportRepository;
import com.gnivc.model.TransportInfo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransportService {
    private final TransportRepository transportRepository;
    private final TransportMapper transportMapper;

    @Transactional
    public void addTransport(TransportInfo transportInfo) {
        Transport transport = transportMapper.toTransport(transportInfo);
        transportRepository.save(transport);
    }

    public Transport getTransport(UUID transportId) {
        return transportRepository.findById(transportId)
            .orElseThrow(() -> new NotFoundException(Transport.class, transportId));
    }
}
