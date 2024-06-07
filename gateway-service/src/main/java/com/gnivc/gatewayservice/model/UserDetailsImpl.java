package com.gnivc.gatewayservice.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
@Getter
public class UserDetailsImpl implements UserDetailsEnhanced {
    private final String userId;
    private final String username;
    private final String password;
    private final List<String> roles;

    public UserDetailsImpl(String userId, String username, List<String> roles) {
        this.userId = userId;
        this.username = username;
        this.roles = roles;
        this.password = null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream().map(Role::new).toList();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String getUserId() {
        return userId;
    }

    @Override
    public List<String> getRoles() {
        return roles;
    }
}
