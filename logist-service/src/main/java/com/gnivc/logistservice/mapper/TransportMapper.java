package com.gnivc.logistservice.mapper;

import com.gnivc.logistservice.model.Transport;
import com.gnivc.model.TransportInfo;
import org.mapstruct.Mapper;

@Mapper
public interface TransportMapper {
    Transport toTransport(TransportInfo transport);
}
