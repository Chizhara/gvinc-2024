package com.gnivc.portalservice.conrtoller;

import com.gnivc.portalservice.dto.UserCreateRequest;
import com.gnivc.portalservice.dto.UserCreateResponse;
import com.gnivc.portalservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/portal")
public class AuthController {

    private final UserService userService;

    @PostMapping("/registrator")
    public UserCreateResponse registerUser(@RequestBody UserCreateRequest userCreateRequest) {
        return userService.createUser(userCreateRequest);
    }

}
