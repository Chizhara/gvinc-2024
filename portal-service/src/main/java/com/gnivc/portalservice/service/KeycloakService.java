package com.gnivc.portalservice.service;

import com.gnivc.portalservice.model.user.dto.UserCreateRequest;
import com.gnivc.portalservice.model.user.UserRole;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.GroupResource;
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

@Service
@RequiredArgsConstructor
public class KeycloakService {

    private final Keycloak keycloak;
    private final PasswordGenerator passwordGenerator;
    @Value("${keycloak.realm}")
    private String realm;

    public UserRepresentation addUser(UserCreateRequest userDto, UserRole userRole) {
        UserRepresentation user = createUser(userDto);
        addRealmRoleToUser(user.getId(), userRole);

        return user;
    }

    public UserRepresentation updatePassword(String userId) {
        CredentialRepresentation credential = createPasswordCredentials();
        UserResource userResource = keycloakRealm().users().get(userId);
        userResource.resetPassword(credential);

        return userResource.toRepresentation();
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

    public void removeGroup(String groupId) {
        keycloakRealm().groups().group(groupId).remove();
    }

    public void addUserToGroup(String userId, String groupId, UserRole userRole) {
        GroupRepresentation group = keycloakRealm().groups().group(groupId).toRepresentation();
        GroupRepresentation groupRepresentation = getSubGroupByRole(group, userRole);
        keycloakRealm().users().get(userId).joinGroup(groupRepresentation.getId());
    }

    public void removeUserFromGroup(String userId, String groupId) {
        keycloakRealm().users().get(userId).leaveGroup(groupId);
    }

    private UserRepresentation createUser(UserCreateRequest userDto) {
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
        try (Response response = keycloakRealm().groups().group(group.getId()).subGroup(subGroup)) {
            if (isSuccess(response)) {
                String subGroupId = extractId(response);
                assignRoleToGroup(subGroupId, userRole);
                subGroup.setId(subGroupId);
                subGroup.setRealmRoles(List.of(userRole.name()));
                return subGroup;
            } else {
                throw new RuntimeException();
            }
        }
    }

    private void assignRoleToGroup(String groupId, UserRole userRole) {
        GroupResource groupResource = keycloakRealm().groups().group(groupId);
        RoleRepresentation role = keycloakRealm().roles().get(userRole.name()).toRepresentation();
        groupResource.roles().realmLevel().add(Collections.singletonList(role));
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

    private GroupRepresentation getSubGroupByRole(GroupRepresentation groupRepresentation, UserRole userRole) {
        return groupRepresentation.getSubGroups().stream()
            .filter(subGroup ->
                subGroup.getRealmRoles().get(0).equals(userRole.name()))
            .findFirst()
            .orElseThrow();
    }

    private RealmResource keycloakRealm() {
        return keycloak.realm(realm);
    }
}
