package com.gnivc.portalservice.service;

import com.gnivc.portalservice.dto.UserCreateRequest;
import com.gnivc.portalservice.dto.UserCreateResponse;
import com.gnivc.portalservice.dto.UserRole;
import com.gnivc.portalservice.mapper.UserMapper;
import com.gnivc.portalservice.model.User;
import com.gnivc.portalservice.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final KeycloakService keycloakService;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Transactional
    public UserCreateResponse createUser(UserCreateRequest userCreateRequest) {
        UserRepresentation userRepresentation = null;
        try {
            userRepresentation = keycloakService.addUser(userCreateRequest, UserRole.REGISTRATOR);
            User user = userMapper.toUser(userRepresentation);
            userRepository.save(user);
            userRepresentation.getCredentials().get(0).getValue();
            return userMapper.toUserCreateResponse(user, userRepresentation);
        } catch (Exception e) {
            if(userRepresentation != null) {
                keycloakService.removeUser(userRepresentation.getId());
            }
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    public boolean isUserExists(String username) {
        return false;
    }
}
