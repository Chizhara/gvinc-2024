package com.gnivc.logistservice.service;

import com.gnivc.logistservice.mapper.DriverMapper;
import com.gnivc.logistservice.model.Company;
import com.gnivc.logistservice.model.Driver;
import com.gnivc.logistservice.repository.CompanyRepository;
import com.gnivc.logistservice.repository.DriverRepository;
import com.gnivc.model.DriverInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DriverService {
    private final DriverRepository driverRepository;
    private final DriverMapper driverMapper;
    private final CompanyRepository companyRepository;

    public void addDriver(DriverInfo driverInfo) {
        UUID userId = driverInfo.getUserId();
        Optional<Driver> driverOpt = driverRepository.findById(userId);
        Optional<Company> companyOpt = companyRepository.findById(userId);
        Company company = companyOpt.orElseGet(() -> addCompany(driverInfo.getAddCompany()));
        Driver driver = driverOpt.orElseGet(() -> addUser(driverInfo));
        driver.setCompanies(List.of(company));
    }

    public Company addCompany(DriverInfo.AddCompanyInfo addCompanyInfo) {
        if (addCompanyInfo == null || addCompanyInfo.getCompanyId() == null) {
            throw new RuntimeException();
        }
        return new Company(addCompanyInfo.getCompanyId());
    }

    public Driver addUser(DriverInfo driverInfo) {
        if (driverInfo.getUserInfo() == null) {
            throw new RuntimeException();
        }
        return driverMapper.toDriver(driverInfo);
    }
}
