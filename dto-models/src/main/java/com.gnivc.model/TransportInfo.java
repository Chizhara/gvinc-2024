package com.gnivc.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransportInfo {
    private UUID id;
    private String vin;
    private LocalDate releaseDate;
    private UUID registerId;
    private UUID companyId;
}
