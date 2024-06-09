package com.gnivc.portalservice.service;

import com.gnivc.portalservice.client.DaDataClient;
import com.gnivc.portalservice.mapper.CompanyMapper;
import com.gnivc.portalservice.model.company.Company;
import com.gnivc.portalservice.model.company.dto.AddUserToCompanyRequest;
import com.gnivc.portalservice.model.company.dto.CompanyCreateRequest;
import com.gnivc.portalservice.model.dadata.DaDataCompanySearch;
import com.gnivc.portalservice.model.dadata.DaDataSuggestionResponse;
import com.gnivc.portalservice.model.user.User;
import com.gnivc.portalservice.model.user.UserRole;
import com.gnivc.portalservice.model.user.dto.UserCreateRequest;
import com.gnivc.portalservice.repository.CompanyRepository;
import com.netflix.config.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.keycloak.representations.idm.GroupRepresentation;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CompanyService {
    private final KeycloakService keycloakService;
    private final CompanyRepository companyRepository;
    private final UserService userService;
    private final CompanyMapper companyMapper;
    private final DaDataClient daDataClient;

    @Transactional
    public void createCompany(CompanyCreateRequest companyCreateRequest, String userId) {
        GroupRepresentation groupRepresentation = null;
        try {
            DaDataSuggestionResponse.Suggestion daDataSuggestionResponse = getCompanyFromDaData(companyCreateRequest.tin());
            Company company = companyMapper.toCompany(daDataSuggestionResponse);
            groupRepresentation = keycloakService
                .createGroup(companyCreateRequest.companyName(), userId);

            company.setId(groupRepresentation.getId());
            company.setName(groupRepresentation.getName());

            User user = userService.getUser(userId);
            company.setUsers(List.of(user));
            companyRepository.saveAndFlush(company);
        } catch (RuntimeException e) {
            if (groupRepresentation != null) {
                keycloakService.removeGroup(groupRepresentation.getId());
            }
            throw new RuntimeException(e); //TODO
        }
    }

    @Transactional
    public void addUserToCompany(AddUserToCompanyRequest addUserToCompanyRequest, String companyName, UserRole userRole) {
        Company company = getCompany(companyName);
        if (addUserToCompanyRequest.getUserCreateRequest() != null) {
            addNewUserToCompany(addUserToCompanyRequest, company, userRole);
            return;
        }
        if (isNotEmpty(addUserToCompanyRequest.getUsername())) {
            addExistsUserToCompany(addUserToCompanyRequest, company, userRole);
            return;
        }
        throw new ValidationException("Username is required");
    }

    public Company getCompany(String companyName) {
        return companyRepository.findByName(companyName)
            .orElseThrow(); //TODO NOT FOUND ex
    }

    private void addExistsUserToCompany(AddUserToCompanyRequest addUserToCompanyRequest, Company company,  UserRole userRole) {
        if (userAssignedToCompany(addUserToCompanyRequest.getUsername(), company)) {
            throw new ValidationException("Username already assigned to company"); //TODO
        }
        User user = userService.getUser(addUserToCompanyRequest.getUsername());
        keycloakService.addUserToGroup(user.getId(), company.getId(), userRole);
        company.getUsers().add(user);
        companyRepository.saveAndFlush(company);
    }

    private void addNewUserToCompany(AddUserToCompanyRequest addUserToCompanyRequest, Company company,  UserRole userRole) {
        User user = null;
        try {
            UserCreateRequest userCreateRequest = addUserToCompanyRequest.getUserCreateRequest();
            userService.createUser(userCreateRequest);
            user = userService.getUserByUsername(userCreateRequest.getUsername());
            keycloakService.addUserToGroup(user.getId(), company.getId(), userRole);
            company.getUsers().add(user);
            companyRepository.saveAndFlush(company);
        } catch (Exception e) {
            if (user != null) {
                keycloakService.removeUserFromGroup(user.getId(), company.getId());
            }
            throw new RuntimeException(e); //TODO
        }

    }

    private DaDataSuggestionResponse.Suggestion getCompanyFromDaData(Long tin) {
        Optional<DaDataSuggestionResponse> daDataSuggestionResponse =
            daDataClient.findByInn(new DaDataCompanySearch(tin.toString(), "LEGAL"));
        if (daDataSuggestionResponse.isEmpty()) {
            throw new RuntimeException(); //TODO
        }
        return daDataSuggestionResponse.get().getSuggestions().get(0);
    }

    private boolean userAssignedToCompany(String username, Company company) {
        return company.getUsers().stream().anyMatch(u -> u.getUsername().equals(username));
    }

    private boolean isNotEmpty(String value) {
        return value != null && !value.isEmpty();
    }

}
