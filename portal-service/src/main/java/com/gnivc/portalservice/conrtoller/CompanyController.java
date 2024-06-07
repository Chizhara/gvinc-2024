package com.gnivc.portalservice.conrtoller;

import com.gnivc.portalservice.dto.CompanyCreateRequest;
import com.gnivc.portalservice.service.CompanyService;
import com.gnivc.portalservice.service.KeycloakService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/portal/company")
public class CompanyController {
    private final CompanyService companyService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createCompany(@RequestHeader("x-userId") String userId,
                              @RequestBody CompanyCreateRequest companyCreateRequest) {
        companyService.createCompany(companyCreateRequest, userId);
    }

    public void addUserToCompany(@RequestHeader("x-userId") String userId,
                                 @RequestBody CompanyCreateRequest companyCreateRequest) {

    }

}
