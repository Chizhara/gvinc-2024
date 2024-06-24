package com.gnivc.driverservice.mapper;

import com.gnivc.driverservice.model.cargo.Cargo;
import com.gnivc.model.CargoInfo;
import org.mapstruct.Mapper;

@Mapper
public interface CargoMapper {
    Cargo toCargo(CargoInfo cargoInfo);
}
