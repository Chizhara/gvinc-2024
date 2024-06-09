package com.gnivc.portalservice.conrtoller;

import com.gnivc.portalservice.model.company.dto.CompanyCreateRequest;
import com.gnivc.portalservice.model.transport.dto.TransportCreateRequest;
import com.gnivc.portalservice.model.transport.dto.TransportCreateResponse;
import com.gnivc.portalservice.service.TransportService;
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
@RequestMapping("/portal")
public class TransportController {
    private final TransportService transportService;
    @PostMapping("/company/{companyId}/transport")
    @ResponseStatus(HttpStatus.CREATED)
    public TransportCreateResponse createCompany(@RequestHeader("x-userId") String userId,
                                                 @PathVariable("companyId") String companyId,
                                                 @RequestBody TransportCreateRequest transportCreateRequest) {
        return transportService.createTransport(transportCreateRequest, userId, companyId);
    }
}
