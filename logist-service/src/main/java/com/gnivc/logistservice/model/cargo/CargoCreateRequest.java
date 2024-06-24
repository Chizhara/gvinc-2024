package com.gnivc.logistservice.model.cargo;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CargoCreateRequest {
    @NotBlank
    String name;
    String description;
}
