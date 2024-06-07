package com.gnivc.portalservice.mapper;

import com.gnivc.portalservice.dto.UserCreateResponse;
import com.gnivc.portalservice.model.User;
import org.keycloak.representations.idm.UserRepresentation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface UserMapper {
    User toUser(UserRepresentation userRepresentation);
    @Mapping(target = "username", source = "user.username")
    @Mapping(target = "password", expression = "java(userRepresentation.getCredentials().get(0).getValue())")
    UserCreateResponse toUserCreateResponse(User user, UserRepresentation userRepresentation);
}
