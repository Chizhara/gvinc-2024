package com.gnivc.portalservice.service;

import com.gnivc.portalservice.model.user.dto.UserCreateRequest;
import com.gnivc.portalservice.model.user.dto.UserCreateResponse;
import com.gnivc.portalservice.model.user.UserRole;
import com.gnivc.portalservice.mapper.UserMapper;
import com.gnivc.portalservice.model.user.User;
import com.gnivc.portalservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final KeycloakService keycloakService;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final EmailService emailService;

    @Transactional
    public UserCreateResponse createUser(UserCreateRequest userCreateRequest) {
        UserRepresentation userRepresentation = null;
        try {
            userRepresentation = keycloakService.addUser(userCreateRequest, UserRole.REGISTRATOR);
            User user = userMapper.toUser(userCreateRequest);
            user.setId(userRepresentation.getId());
            userRepository.saveAndFlush(user);
            emailService.sendPassword(user.getEmail(),
                userRepresentation.getCredentials().get(0).getValue());
            return userMapper.toUserCreateResponse(user, userRepresentation);
        } catch (RuntimeException e) {
            if(userRepresentation != null) {
                keycloakService.removeUser(userRepresentation.getId());
            }
            throw new RuntimeException(e); //TODO
        }
    }

    public User getUser(String userId) {
        return userRepository.findById(userId)
            .orElseThrow(); //TODO notfound ex
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
            .orElseThrow(); //TODO notfound ex
    }

    public UserCreateResponse updatePassword(String userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(); //TODO notfound
        UserRepresentation userRepresentation = keycloakService.updatePassword(userId);
        return userMapper.toUserCreateResponse(user, userRepresentation);
    }

    public boolean isUserExists(String username) {
        return userRepository.existsByUsername(username);
    }
}
