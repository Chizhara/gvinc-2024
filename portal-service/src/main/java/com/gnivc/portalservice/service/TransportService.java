package com.gnivc.portalservice.service;

import com.gnivc.portalservice.mapper.TransportMapper;
import com.gnivc.portalservice.model.company.Company;
import com.gnivc.portalservice.model.transport.Transport;
import com.gnivc.portalservice.model.transport.dto.TransportCreateRequest;
import com.gnivc.portalservice.model.transport.dto.TransportCreateResponse;
import com.gnivc.portalservice.model.user.User;
import com.gnivc.portalservice.repository.TransportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransportService {
    private final TransportRepository transportRepository;
    private final CompanyService companyService;
    private final UserService userService;
    private final TransportMapper transportMapper;

    public TransportCreateResponse createTransport(TransportCreateRequest request, String userId, String companyId) {
        User user = userService.getUser(userId);
        Company company = companyService.getCompany(companyId);
        Transport transport = transportMapper.toTransport(request);
        transport.setCompany(company);
        transport.setRegister(user);
        transportRepository.save(transport);
        return transportMapper.toTransportCreateResponse(transport);
    }
}
