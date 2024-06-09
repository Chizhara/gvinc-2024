package com.gnivc.portalservice.repository;

import com.gnivc.portalservice.model.company.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, String> {
    Optional<Company> findByName(String companyName);
}
