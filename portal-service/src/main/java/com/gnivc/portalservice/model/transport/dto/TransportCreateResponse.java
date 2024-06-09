package com.gnivc.portalservice.model.transport.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class TransportCreateResponse {
    private UUID username;
    private final String vin;
    private final LocalDate releaseDate;
}
