package com.gnivc.logistservice.mapper;

import com.gnivc.logistservice.model.transport.Transport;
import com.gnivc.model.TransportInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface TransportMapper {
    @Mapping(target = "company", ignore = true)
    Transport toTransport(TransportInfo transport);
}
