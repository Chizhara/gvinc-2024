package com.gnivc.gatewayservice.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Getter
public class AuthenticationImpl implements Authentication {
    private String companyId;
    private String token;
    @Setter
    private UserDetailsImpl details;

    public AuthenticationImpl(String token) {
        this.token = token;
    }

    public AuthenticationImpl(String token, String companyId) {
        this.companyId = companyId;
        this.token = token;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return details.getAuthorities();
    }

    @Override
    public Object getCredentials() {
        return token;
    }

    @Override
    public Object getDetails() {
        return details;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }

    @Override
    public boolean isAuthenticated() {
        return true;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

    }

    @Override
    public String getName() {
        return details.getUsername();
    }
}
