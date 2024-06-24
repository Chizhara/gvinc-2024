package com.gnivc.driverservice.service;

import com.gnivc.driverservice.mapper.CargoMapper;
import com.gnivc.driverservice.model.cargo.Cargo;
import com.gnivc.driverservice.repository.CargoRepository;
import com.gnivc.model.CargoInfo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CargoService {
    private final CargoRepository cargoRepository;
    private final CargoMapper cargoMapper;

    @Transactional
    public void addCargo(CargoInfo cargoInfo) {
        Cargo cargo = cargoMapper.toCargo(cargoInfo);
        cargoRepository.save(cargo);
    }
}
