package com.gnivc.portalservice.mapper;

import com.gnivc.portalservice.model.user.dto.UserCreateRequest;
import com.gnivc.portalservice.model.user.dto.UserCreateResponse;
import com.gnivc.portalservice.model.user.User;
import org.keycloak.representations.idm.UserRepresentation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface UserMapper {
    User toUser(UserCreateRequest userCreateRequest);
    @Mapping(target = "username", source = "user.username")
    @Mapping(target = "email", source = "user.email")
    @Mapping(target = "password", expression = "java(userRepresentation.getCredentials().get(0).getValue())")
    UserCreateResponse toUserCreateResponse(User user, UserRepresentation userRepresentation);
}
