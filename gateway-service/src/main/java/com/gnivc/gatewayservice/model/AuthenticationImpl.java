package com.gnivc.gatewayservice.model;

import lombok.Getter;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthenticationToken;

public class AuthenticationImpl extends BearerTokenAuthenticationToken {
    @Getter
    private String companyId;

    public AuthenticationImpl(String token) {
        super(token);
    }

    public AuthenticationImpl(String token, String companyId) {
        super(token);
        this.companyId = companyId;
    }

}
