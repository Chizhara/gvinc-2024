package com.gnivc.portalservice.service;

import com.gnivc.portalservice.dto.UserCreateRequest;
import com.gnivc.portalservice.dto.UserRole;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.GroupRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class KeycloakService {

    private final Keycloak keycloak;
    private final PasswordGenerator passwordGenerator;
    @Value("${keycloak.realm}")
    private String realm;

    public UserRepresentation addUser(UserCreateRequest userDto, UserRole userRole) {
        UserRepresentation user = createUser(userDto, userRole);
        addRealmRoleToUser(user.getId(), userRole);

        return user;
    }

    public void removeUser(String userId) {
        keycloakRealm().users().get(userId).remove();
    }

    public GroupRepresentation createGroup(String groupName, String userId) {
        GroupRepresentation group = createGroup(groupName);
        List<GroupRepresentation> subGroups = createSubGroups(group);
        group.setSubGroups(subGroups);
        addUserToGroup(userId, group);
        return group;
    }

    public void addUserToGroup(String groupId) {

    }

    private UserRepresentation createUser(UserCreateRequest userDto, UserRole userRole) {
        UserRepresentation user = new UserRepresentation();
        CredentialRepresentation credential = createPasswordCredentials();

        user.setUsername(userDto.getUsername());
        user.setCredentials(Collections.singletonList(credential));
        user.setEnabled(true);
        return createUser(user);
    }

    private UserRepresentation createUser(UserRepresentation user) {
        try (Response response = keycloakRealm().users().create(user)) {
            if (isSuccess(response)) {
                String userId = extractId(response);
                user.setId(userId);
                return user;
            } else {
                throw new RuntimeException();
            }
        }
    }

    private void addRealmRoleToUser(String userId, UserRole userRole) {
        UserResource userResource = keycloakRealm().users().get(userId);
        RoleRepresentation role = keycloakRealm().roles().get(userRole.name()).toRepresentation();

        userResource.roles()
            .realmLevel()
            .add(Collections.singletonList(role));
    }

    private GroupRepresentation createGroup(String groupName) {
        GroupRepresentation groupRepresentation = new GroupRepresentation();
        groupRepresentation.setName(groupName);
        try (Response response = keycloakRealm().groups().add(groupRepresentation)) {
            if (response.getStatusInfo().getFamily().equals(Response.Status.Family.SUCCESSFUL)) {
                String groupId = extractId(response);
                groupRepresentation.setId(groupId);
                return groupRepresentation;
            } else {
                throw new RuntimeException();
            }
        }
    }

    private CredentialRepresentation createPasswordCredentials() {
        CredentialRepresentation passwordCredentials = new CredentialRepresentation();
        passwordCredentials.setTemporary(false);
        passwordCredentials.setType(CredentialRepresentation.PASSWORD);
        passwordCredentials.setValue(passwordGenerator.generatePassword());
        return passwordCredentials;
    }

    private List<GroupRepresentation> createSubGroups(GroupRepresentation group) {
        return List.of(
            createSubGroup(group, UserRole.ADMIN),
            createSubGroup(group, UserRole.LOGIST),
            createSubGroup(group, UserRole.DRIVER)
        );
    }

    private GroupRepresentation createSubGroup(GroupRepresentation group, UserRole userRole) {
        GroupRepresentation subGroup = new GroupRepresentation();
        subGroup.setName(group.getName() + "_" + userRole);
        subGroup.setRealmRoles(List.of(userRole.name()));
        try (Response response = keycloakRealm().groups().group(group.getId()).subGroup(subGroup)) {
            if (isSuccess(response)) {
                String subGroupId = extractId(response);
                subGroup.setId(subGroupId);
                return subGroup;
            } else {
                throw new RuntimeException();
            }
        }
    }

    private void addUserToGroup(String userId, GroupRepresentation group) {
        keycloakRealm().users().get(userId).joinGroup(
            group.getSubGroups()
                .stream()
                .filter(subGroup ->
                    subGroup.getName()
                        .equals(group.getName() + "_" + UserRole.ADMIN))
                .findFirst()
                .orElseThrow()
                .getId());
    }

    private String extractId(Response response) {
        String path = (String) response.getHeaders().getFirst("Location");
        return path.substring(path.lastIndexOf("/") + 1);
    }

    private boolean isSuccess(Response response) {
        return response.getStatusInfo().getFamily() == Response.Status.Family.SUCCESSFUL;
    }


    private RealmResource keycloakRealm() {
        return keycloak.realm(realm);
    }
}
