package com.gnivc.portalservice.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserCreateResponse {
    private String username;
    private String password;
}
