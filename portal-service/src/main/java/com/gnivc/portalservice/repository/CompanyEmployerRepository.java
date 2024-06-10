package com.gnivc.portalservice.repository;

import com.gnivc.portalservice.model.company.CompanyEmployer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyEmployerRepository extends JpaRepository<CompanyEmployer, CompanyEmployer.CompanyUserId>  {
}
