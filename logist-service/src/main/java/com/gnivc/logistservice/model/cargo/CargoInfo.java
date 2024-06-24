package com.gnivc.logistservice.model.cargo;

import lombok.Data;

import java.util.UUID;

@Data
public class CargoInfo {
    private UUID id;
    private String name;
    private String description;
}
