package com.gnivc.logistservice.service;

import com.gnivc.commonexception.exception.NotFoundException;
import com.gnivc.logistservice.mapper.DriverMapper;
import com.gnivc.logistservice.model.company.Company;
import com.gnivc.logistservice.model.driver.Driver;
import com.gnivc.logistservice.repository.CompanyRepository;
import com.gnivc.logistservice.repository.DriverRepository;
import com.gnivc.model.DriverInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DriverService {
    private final DriverRepository driverRepository;
    private final DriverMapper driverMapper;
    private final CompanyRepository companyRepository;
    private final KafkaTemplate<String, DriverInfo> driverKafkaTemplate;
    @Value("${spring.kafka.topic.driver.buffer.name}")
    private String driverTopic;

    @Transactional
    public void addDriver(DriverInfo driverInfo) {
        UUID userId = driverInfo.getUserId();
        Optional<Driver> driverOpt = driverRepository.findById(userId);
        Company company = getOrCreateCompany(driverInfo);
        Driver driver = driverOpt.orElseGet(() -> addUser(driverInfo));

        if(driver.getCompanies() == null) {
            driver.setCompanies(List.of(company));
        } else {
            driver.getCompanies().add(company);
        }

        driverRepository.saveAndFlush(driver);
    }

    public Driver getDriver(UUID driverId) {
        return driverRepository.findById(driverId)
            .orElseThrow(() -> new NotFoundException(Driver.class, driverId));
    }

    private Company getOrCreateCompany(DriverInfo driverInfo) {
        if (driverInfo.getAddCompany() == null || driverInfo.getAddCompany().getCompanyId() == null) {
            sendBuf(driverInfo);
        }
        Optional<Company> companyOpt = companyRepository.findById(driverInfo.getAddCompany().getCompanyId());
        Company company = companyOpt.orElseGet(() -> new Company(driverInfo.getAddCompany().getCompanyId()));
        if(companyOpt.isEmpty()) {
            companyRepository.saveAndFlush(company);
        }

        return company;
    }

    private Driver addUser(DriverInfo driverInfo) {
        if (driverInfo.getUserInfo() == null) {
            throw new RuntimeException();
        }
        return driverMapper.toDriver(driverInfo);
    }

    private void sendBuf(DriverInfo driverInfo) {
        driverKafkaTemplate.send(driverTopic, driverInfo);
    }
}
