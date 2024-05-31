package com.gnivc.gatewayservice.congifure;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;

@RequiredArgsConstructor
public class Role implements GrantedAuthority, Serializable {
    private final String name;
    @Override
    public String getAuthority() {
        return name;
    }
}
