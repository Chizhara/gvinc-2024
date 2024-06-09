package com.gnivc.portalservice.model.company.dto;

import com.gnivc.portalservice.model.user.User;
import com.gnivc.portalservice.model.user.UserRole;
import com.gnivc.portalservice.model.user.dto.UserCreateRequest;
import com.gnivc.portalservice.validation.EnumFilter;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AddUserToCompanyRequest {
    @NotBlank
    private final String userId;
    @NotNull
    private final UserCreateRequest userCreateRequest;

}
