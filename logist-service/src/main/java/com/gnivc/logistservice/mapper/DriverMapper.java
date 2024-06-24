package com.gnivc.logistservice.mapper;

import com.gnivc.logistservice.model.driver.Driver;
import com.gnivc.model.DriverInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface DriverMapper {
    @Mapping(target = "id", source = "userId")
    @Mapping(target = "username", source = "userInfo.username")
    @Mapping(target = "name", source = "userInfo.name")
    @Mapping(target = "surname", source = "userInfo.surname")
    @Mapping(target = "companies", ignore = true)
    Driver toDriver(DriverInfo driverInfo);
}
