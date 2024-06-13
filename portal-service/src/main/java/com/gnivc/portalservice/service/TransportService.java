package com.gnivc.portalservice.service;

import com.gnivc.model.TransportInfo;
import com.gnivc.portalservice.mapper.CompanyMapper;
import com.gnivc.portalservice.mapper.TransportMapper;
import com.gnivc.portalservice.model.company.Company;
import com.gnivc.portalservice.model.transport.Transport;
import com.gnivc.portalservice.model.transport.dto.TransportCreateRequest;
import com.gnivc.portalservice.model.transport.dto.TransportCreateResponse;
import com.gnivc.portalservice.model.user.User;
import com.gnivc.portalservice.repository.TransportRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransportService {
    private final TransportRepository transportRepository;
    private final CompanyService companyService;
    private final UserService userService;
    private final TransportMapper transportMapper;
    private final KafkaTemplate<String, TransportInfo> kafkaTemplate;
    @Value("${spring.kafka.topic.transport.name}")
    private String transportTopic;

    @Transactional
    public TransportCreateResponse createTransport(TransportCreateRequest request, String userId, String companyId) {
        User user = userService.getUser(userId);
        Company company = companyService.getCompany(companyId);
        Transport transport = transportMapper.toTransport(request);
        transport.setCompany(company);
        transport.setRegister(user);
        transportRepository.save(transport);
        send(transport);
        return transportMapper.toTransportCreateResponse(transport);
    }

    private void send(Transport transport) {
        kafkaTemplate.send(transportTopic, transportMapper.toTransportInfo(transport));
    }
}
