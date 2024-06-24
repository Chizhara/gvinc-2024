package com.gnivc.logistservice.controller;

import com.gnivc.logistservice.model.cargo.CargoCreateRequest;
import com.gnivc.logistservice.model.cargo.CargoInfo;
import com.gnivc.logistservice.service.CargoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/logist/company/{companyId}/cargo")
public class CargoController {
    private final CargoService cargoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CargoInfo createCargo(@RequestBody CargoCreateRequest cargoCreateRequest) {
        return cargoService.addCargo(cargoCreateRequest);
    }

    @GetMapping
    public List<CargoInfo> getCargos(@RequestParam(defaultValue = "0") int from,
                                     @RequestParam(defaultValue = "10") int size) {
        return cargoService.getCargos(from, size);
    }
}
