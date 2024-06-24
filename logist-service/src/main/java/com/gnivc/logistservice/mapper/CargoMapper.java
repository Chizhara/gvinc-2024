package com.gnivc.logistservice.mapper;

import com.gnivc.logistservice.model.cargo.Cargo;
import com.gnivc.logistservice.model.cargo.CargoCreateRequest;
import com.gnivc.logistservice.model.cargo.CargoInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface CargoMapper {
    @Mapping(target = "id", ignore = true)
    Cargo toCargo(CargoCreateRequest cargoCreateRequest);

    CargoInfo toCargoInfo(Cargo cargo);

    List<CargoInfo> toCargosInfo(List<Cargo> cargos);
}
