package com.gnivc.portalservice.mapper;

import com.gnivc.model.TransportInfo;
import com.gnivc.portalservice.model.transport.Transport;
import com.gnivc.portalservice.model.transport.dto.TransportCreateRequest;
import com.gnivc.portalservice.model.transport.dto.TransportCreateResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface TransportMapper {
    @Mapping(target = "registerId", source = "register.id")
    @Mapping(target = "companyId", source = "company.id")
    TransportInfo toTransportInfo(Transport transport);
    Transport toTransport(TransportCreateRequest transportCreateRequest);
    @Mapping(target = "username", source = "register.id")
    TransportCreateResponse toTransportCreateResponse(Transport transport);
}
