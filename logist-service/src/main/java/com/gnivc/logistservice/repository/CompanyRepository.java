package com.gnivc.logistservice.repository;

import com.gnivc.logistservice.model.company.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CompanyRepository extends JpaRepository<Company, UUID> {
}
