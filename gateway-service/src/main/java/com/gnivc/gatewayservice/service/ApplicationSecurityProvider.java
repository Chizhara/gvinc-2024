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
            getRealmRole(user); //TODO можно пересмотреть методы
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
        GroupResource company = getCompany(user.toRepresentation().getId(), companyId);
        List<GroupRepresentation> userCompanies = user.groups();
        GroupRepresentation roleGroup = company.toRepresentation()
            .getSubGroups()
            .stream()
            .filter(groupRepresentation ->
                userCompanies.stream()
                    .anyMatch(temp -> groupRepresentation.getId().equals(temp.getId())))
            .findFirst()
            .orElseThrow();

        return roleGroup.getRealmRoles().get(0);
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

    private GroupResource getCompany(String userId, String companyId) {
        GroupResource companyResource = keycloak.realm(realm).groups().group(companyId);

        companyResource.members()
            .stream()
            .filter(member ->
                member.getId().equals(userId))
            .findFirst()
            .orElseThrow();

        return companyResource;
    }

    private Mono<Jwt> getJwt(BearerTokenAuthenticationToken bearerTokenAuthentication) {
        return jwtDecoder.decode(bearerTokenAuthentication.getToken().substring(7));
    }

    private boolean isCompanyEmpty(AuthenticationImpl auth) {
        return auth.getCompanyId() == null || auth.getCompanyId().isBlank();
    }

}
