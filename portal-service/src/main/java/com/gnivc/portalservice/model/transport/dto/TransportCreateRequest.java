package com.gnivc.portalservice.model.transport.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class TransportCreateRequest {
    @NotBlank
    private final String vin;
    @NotBlank
    private final String gosNumber;
    @NotNull
    private final LocalDate releaseDate;
}
