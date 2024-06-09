package com.gnivc.portalservice.conrtoller;

import com.gnivc.portalservice.model.company.dto.AddUserToCompanyRequest;
import com.gnivc.portalservice.model.company.dto.CompanyCreateRequest;
import com.gnivc.portalservice.model.user.UserRole;
import com.gnivc.portalservice.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
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

    @PostMapping("/{companyName}/user/admin")
    public void addAdminToCompany(@PathVariable String companyName,
                                 @RequestBody AddUserToCompanyRequest addUserToCompanyRequest) {
        companyService.addUserToCompany(addUserToCompanyRequest, companyName, UserRole.ADMIN);
    }

    @PostMapping("/{companyName}/user")
    public void addLogistToCompany(@PathVariable String companyName,
                                 @RequestBody AddUserToCompanyRequest addUserToCompanyRequest) {
        companyService.addUserToCompany(addUserToCompanyRequest, companyName, UserRole.LOGIST);
    }

    @PostMapping("/{companyName}/user/driver")
    public void addDriverToCompany(@PathVariable String companyName,
                                 @RequestBody AddUserToCompanyRequest addUserToCompanyRequest) {
        companyService.addUserToCompany(addUserToCompanyRequest, companyName, UserRole.DRIVER);
    }

}
