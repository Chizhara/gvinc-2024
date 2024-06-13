package com.gnivc.logistservice.service;

import com.gnivc.logistservice.mapper.TransportMapper;
import com.gnivc.logistservice.model.Company;
import com.gnivc.logistservice.model.Transport;
import com.gnivc.logistservice.repository.CompanyRepository;
import com.gnivc.logistservice.repository.TransportRepository;
import com.gnivc.model.TransportInfo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransportService {
    private final TransportRepository transportRepository;
    private final CompanyRepository companyRepository;
    private final KafkaTemplate<String, TransportInfo> kafkaTemplate;
    private final TransportMapper transportMapper;
    @Value("${spring.kafka.topic.transport.name}")
    private String transportTopic;

    @Transactional
    public void addTransport(TransportInfo transportInfo) {
        UUID companyId = transportInfo.getCompanyId();
        Optional<Company> companyOptional = companyRepository.findById(companyId);
        if(companyOptional.isEmpty()) {
            kafkaTemplate.send(transportTopic, transportInfo);
            return;
        }
        Transport transport = transportMapper.toTransport(transportInfo);
        transport.setCompany(companyOptional.get());
        transportRepository.save(transport);
    }
}
