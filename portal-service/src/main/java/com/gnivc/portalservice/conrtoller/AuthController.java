package com.gnivc.portalservice.conrtoller;

import com.gnivc.portalservice.model.user.dto.UserCreateRequest;
import com.gnivc.portalservice.model.user.dto.UserCreateResponse;
import com.gnivc.portalservice.model.user.dto.UserUpdateRequest;
import com.gnivc.portalservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/portal")
public class AuthController {

    private final UserService userService;

    @PostMapping("/registrator")
    @ResponseStatus(HttpStatus.CREATED)
    public UserCreateResponse registerUser(@RequestBody UserCreateRequest userCreateRequest) {
        return userService.createUser(userCreateRequest);
    }

    @PutMapping("/password")
    public UserCreateResponse updateUser(@RequestBody String userId) {
        return userService.updatePassword(userId);
    }

    @PutMapping("/users/user")
    public UserCreateResponse updateUser(@RequestHeader("x-userId") String userId,
                                         @RequestBody UserUpdateRequest userCreateRequest) {
        return userService.updateUser(userCreateRequest, userId);
    }
}
