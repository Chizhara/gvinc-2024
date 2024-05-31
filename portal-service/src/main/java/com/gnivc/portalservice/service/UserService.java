package com.gnivc.portalservice.service;

import com.gnivc.portalservice.dto.UserCreateRequest;
import com.gnivc.portalservice.dto.UserCreateResponse;
import com.gnivc.portalservice.dto.UserRole;
import lombok.RequiredArgsConstructor;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final KeycloakService keycloakService;


    public UserCreateResponse createUser(UserCreateRequest userCreateRequest) {
        UserRepresentation userRepresentation = keycloakService.addUser(userCreateRequest, UserRole.REGISTRATOR);
        return UserCreateResponse.builder()
            .username(userRepresentation.getUsername())
            .password(userRepresentation.getCredentials().get(0).getValue())
            .build();
    }
}
