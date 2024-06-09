package com.gnivc.gatewayservice.service;

import com.gnivc.gatewayservice.model.AuthenticationImpl;
import com.gnivc.gatewayservice.model.UserDetailsImpl;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.GroupResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.GroupRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthenticationToken;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Primary
@Component
public class ApplicationSecurityProvider implements ReactiveAuthenticationManager {
    private final Keycloak keycloak;
    private final String USERNAME_CLAIM = "preferred_username";
    private final String USER_ID_CLAIM = "sub";


    private ReactiveJwtDecoder jwtDecoder;
    @Value("${keycloak.realm}")
    private String realm;


    public ApplicationSecurityProvider(ReactiveJwtDecoder jwtDecoder, Keycloak keycloak) {
        this.jwtDecoder = jwtDecoder;
        this.keycloak = keycloak;
    }

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) throws JwtException {
        AuthenticationImpl auth = (AuthenticationImpl) authentication;

        return getJwt(auth).map(jwt -> {
            String userId = jwt.getClaimAsString(USER_ID_CLAIM);
            String username = jwt.getClaimAsString(USERNAME_CLAIM);
            UserResource user = getUser(userId);
            List<String> roles = getUserRole(user, auth);
            auth.setDetails(new UserDetailsImpl(userId, username, null, roles));
            auth.setAuthenticated(true);
            return auth;
        });
    }

    private UserResource getUser(String userid) {
        UserResource userResource = keycloak.realm(realm).users().get(userid);
        return userResource;
    }

    private List<String> getUserRole(UserResource user, AuthenticationImpl authentication) {
        List<String> roles = new ArrayList<>();
        RoleRepresentation realmRole = getRealmRole(user);
        if (realmRole != null) {
            roles.add(realmRole.getName());
        }
        if (!isCompanyEmpty(authentication)) {
            roles.add(getCompanyRole(user, authentication.getCompanyId()));
        }

        return roles;
    }

    private String getCompanyRole(UserResource user, String companyId) {
        GroupRepresentation company = getCompany(user.toRepresentation().getId(), companyId);

        return keycloak.realm(realm).groups().group(company.getId()).roles().realmLevel().listAll().get(0).getName();
    }

    private RoleRepresentation getRealmRole(UserResource user) {
        return user.roles()
            .realmLevel()
            .listAll()
            .stream()
            .filter(roleRepresentation ->
                !roleRepresentation.getName().equals("default-roles-gvinc "))
            .findFirst()
            .orElse(null);
    }

    private GroupRepresentation getCompany(String userId, String companyId) {
        GroupRepresentation companyRep = keycloak.realm(realm).groups().group(companyId).toRepresentation();
        return getSubGroup(companyRep, userId);
    }

    private GroupRepresentation findCompanyByName(String companyName) {
        return keycloak.realm(realm).groups().groups().stream()
            .filter(group -> group.getName().equals(companyName))
            .findFirst().orElseThrow();
    }

    private GroupRepresentation getSubGroup(GroupRepresentation group, String userId) {
        List<GroupRepresentation> subGroups = group.getSubGroups();
        return subGroups.stream()
            .filter(subGroup -> containsMember(subGroup, userId))
            .findFirst()
            .orElseThrow();
    }

    private boolean containsMember(GroupRepresentation subGroup, String memberId) {
        return keycloak.realm(realm).groups().group(subGroup.getId()).members().stream()
            .anyMatch(member -> member.getId().equals(memberId));
    }

    private Mono<Jwt> getJwt(BearerTokenAuthenticationToken bearerTokenAuthentication) {
        return jwtDecoder.decode(bearerTokenAuthentication.getToken().substring(7));
    }

    private boolean isCompanyEmpty(AuthenticationImpl auth) {
        return auth.getCompanyId() == null || auth.getCompanyId().isBlank();
    }

}
