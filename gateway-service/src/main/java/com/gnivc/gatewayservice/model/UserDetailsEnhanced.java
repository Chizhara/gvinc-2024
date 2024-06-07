package com.gnivc.gatewayservice.model;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface UserDetailsEnhanced extends UserDetails {
    String getUserId();
    List<String> getRoles();
}
