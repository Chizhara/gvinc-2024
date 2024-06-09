package com.gnivc.portalservice.mapper;

import com.gnivc.portalservice.model.company.Company;
import com.gnivc.portalservice.model.dadata.DaDataSuggestionResponse;
import org.keycloak.representations.idm.GroupRepresentation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface CompanyMapper {
    @Mapping(target = "inn", source = "data.inn")
    @Mapping(target = "kpp", source = "data.kpp")
    @Mapping(target = "ogrn", source = "data.ogrn")
    @Mapping(target = "address", source = "data.address.value")
    Company toCompany(DaDataSuggestionResponse.Suggestion response);
}
