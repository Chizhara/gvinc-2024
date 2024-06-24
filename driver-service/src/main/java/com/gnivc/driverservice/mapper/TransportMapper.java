package com.gnivc.driverservice.mapper;

import com.gnivc.driverservice.model.transport.Transport;
import com.gnivc.driverservice.model.transport.TransportInfoReponse;
import com.gnivc.model.TransportInfo;
import org.mapstruct.Mapper;

@Mapper
public interface TransportMapper {
    Transport toTransport(TransportInfo transportInfo);
    TransportInfoReponse toTransportInfoResponse(Transport transport);
}
