package com.gnivc.portalservice.model.company.dto;

import com.gnivc.portalservice.model.user.dto.UserCreateRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AddUserToCompanyRequest {
    private final String userId;
    private final UserCreateRequest userCreateRequest;

}
