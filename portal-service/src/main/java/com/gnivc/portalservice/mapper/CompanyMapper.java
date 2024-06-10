package com.gnivc.portalservice.mapper;

import com.gnivc.portalservice.model.company.Company;
import com.gnivc.portalservice.model.company.dto.CompanyInfo;
import com.gnivc.portalservice.model.company.dto.CompanyShortInfo;
import com.gnivc.portalservice.model.dadata.DaDataSuggestionResponse;
import org.keycloak.representations.idm.GroupRepresentation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface CompanyMapper {
    @Mapping(target = "inn", source = "data.inn")
    @Mapping(target = "kpp", source = "data.kpp")
    @Mapping(target = "ogrn", source = "data.ogrn")
    @Mapping(target = "address", source = "data.address.value")
    Company toCompany(DaDataSuggestionResponse.Suggestion response);

    CompanyInfo toCompanyInfo(Company company);
    CompanyShortInfo toCompanyShortInfo(Company company);
    List<CompanyShortInfo> toCompanyShortInfo(List<Company> company);
}
