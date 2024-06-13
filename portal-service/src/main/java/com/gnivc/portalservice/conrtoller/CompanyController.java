package com.gnivc.portalservice.conrtoller;

import com.gnivc.portalservice.PageableGenerator;
import com.gnivc.portalservice.model.company.dto.AddUserToCompanyRequest;
import com.gnivc.portalservice.model.company.dto.CompanyCreateRequest;
import com.gnivc.portalservice.model.company.dto.CompanyInfo;
import com.gnivc.portalservice.model.company.dto.CompanyShortInfo;
import com.gnivc.portalservice.model.user.UserRole;
import com.gnivc.portalservice.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/portal/company")
public class CompanyController {
    private final CompanyService companyService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CompanyInfo createCompany(@RequestHeader("x-userId") String userId,
                              @RequestBody CompanyCreateRequest companyCreateRequest) {
        return companyService.createCompany(companyCreateRequest, userId);
    }

    @GetMapping("/{companyId}")
    @ResponseStatus
    public CompanyInfo getCompanyInfo(@PathVariable String companyId) {
        return companyService.getCompanyInfo(companyId);
    }

    @GetMapping
    @ResponseStatus
    public List<CompanyShortInfo> getCompanyInfo(@RequestParam(defaultValue = "0") int from,
                                                 @RequestParam(defaultValue = "10") int size) {
        return companyService.getCompanyInfo(PageableGenerator.getPageable(from, size));
    }

    @PostMapping("/{companyId}/user/admin")
    @ResponseStatus(HttpStatus.CREATED)
    public void addAdminToCompany(@PathVariable String companyId,
                                 @RequestBody AddUserToCompanyRequest addUserToCompanyRequest) {
        companyService.addUserToCompany(addUserToCompanyRequest, companyId, UserRole.ADMIN);
    }

    @PostMapping("/{companyId}/user/logist")
    @ResponseStatus(HttpStatus.CREATED)
    public void addLogistToCompany(@PathVariable String companyId,
                                 @RequestBody AddUserToCompanyRequest addUserToCompanyRequest) {
        companyService.addUserToCompany(addUserToCompanyRequest, companyId, UserRole.LOGIST);
    }

    @PostMapping("/{companyId}/user/driver")
    @ResponseStatus(HttpStatus.CREATED)
    public void addDriverToCompany(@PathVariable String companyId,
                                 @RequestBody AddUserToCompanyRequest addUserToCompanyRequest) {
        companyService.addUserToCompany(addUserToCompanyRequest, companyId, UserRole.DRIVER);
    }

}
