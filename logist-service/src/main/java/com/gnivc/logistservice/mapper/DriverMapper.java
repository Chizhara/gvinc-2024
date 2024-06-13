package com.gnivc.logistservice.mapper;

import com.gnivc.logistservice.model.Driver;
import com.gnivc.model.DriverInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface DriverMapper {
    @Mapping(target = "username", source = "userInfo.username")
    @Mapping(target = "name", source = "userInfo.name")
    @Mapping(target = "surname", source = "userInfo.surname")
    Driver toDriver(DriverInfo driverInfo);
}
