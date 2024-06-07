package com.gnivc.portalservice.service;

import com.gnivc.portalservice.dto.CompanyCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompanyService {
    private final KeycloakService keycloakService;

    public void createCompany(CompanyCreateRequest companyCreateRequest, String userId) {
        keycloakService.createGroup(companyCreateRequest.companyName(), userId);
    }

    public void addUserToCompany(CompanyCreateRequest companyCreateRequest, String userId) {

    }

}
