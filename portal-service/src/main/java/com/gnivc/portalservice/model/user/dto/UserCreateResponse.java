package com.gnivc.portalservice.model.user.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserCreateResponse {
    private String username;
    private String name;
    private String surname;
    private String email;
    private String password;
}
