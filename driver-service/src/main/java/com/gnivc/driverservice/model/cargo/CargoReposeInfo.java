package com.gnivc.driverservice.model.cargo;

import lombok.Data;

import java.util.UUID;

@Data
public class CargoReposeInfo {
    private UUID id;
    private String nome;
    private String description;
}
