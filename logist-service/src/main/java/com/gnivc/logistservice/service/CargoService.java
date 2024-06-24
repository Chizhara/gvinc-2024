package com.gnivc.logistservice.service;

import com.gnivc.logistservice.PageableGenerator;
import com.gnivc.logistservice.mapper.CargoMapper;
import com.gnivc.logistservice.model.cargo.Cargo;
import com.gnivc.logistservice.model.cargo.CargoCreateRequest;
import com.gnivc.logistservice.model.cargo.CargoInfo;
import com.gnivc.logistservice.repository.CargoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CargoService {
    private final KafkaTemplate<String, com.gnivc.model.CargoInfo> cargoKafkaTemplate;
    private final CargoMapper cargoMapper;
    private final CargoRepository cargoRepository;
    @Value("${spring.kafka.topic.cargo.name}")
    private String cargoTopic;

    @Transactional
    public CargoInfo addCargo(CargoCreateRequest cargoCreateRequest) {
        Cargo cargo = cargoMapper.toCargo(cargoCreateRequest);
        cargoRepository.saveAndFlush(cargo);
        sendCargo(cargo);
        return cargoMapper.toCargoInfo(cargo);
    }

    public List<CargoInfo> getCargos(int from, int size) {
        List<Cargo> cargos = cargoRepository
            .findAll(PageableGenerator.getPageable(from, size))
            .toList();
        return cargoMapper.toCargosInfo(cargos);
    }

    private void sendCargo(Cargo cargo) {
        cargoKafkaTemplate.send(cargoTopic,
            new com.gnivc.model.CargoInfo(
                cargo.getId(),
                cargo.getName(),
                cargo.getDescription()));
    }
}
