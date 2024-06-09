package com.gnivc.portalservice.mapper;

import com.gnivc.portalservice.model.transport.Transport;
import com.gnivc.portalservice.model.transport.dto.TransportCreateRequest;
import com.gnivc.portalservice.model.transport.dto.TransportCreateResponse;
import org.mapstruct.Mapper;

@Mapper
public interface TransportMapper {
    Transport toTransport(TransportCreateRequest transportCreateRequest);
    TransportCreateResponse toTransportCreateResponse(Transport transport);
}
