package com.gnivc.logistservice.repository;

import com.gnivc.logistservice.model.Company;
import com.gnivc.logistservice.model.Driver;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CompanyRepository extends JpaRepository<Company, UUID> {
}
