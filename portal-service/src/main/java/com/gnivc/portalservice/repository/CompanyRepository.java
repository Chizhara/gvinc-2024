package com.gnivc.portalservice.repository;

import com.gnivc.portalservice.model.company.Company;
import com.gnivc.portalservice.model.user.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, String> {
    Optional<Company> findByName(String companyName);
    @Query(value = "SELECT COUNT(*) FROM companies_users " +
        "WHERE company_id = ?1 AND role = ?2", nativeQuery = true)
    Integer countByCompanyNameAndRoles(String companyId, UserRole role);
}
