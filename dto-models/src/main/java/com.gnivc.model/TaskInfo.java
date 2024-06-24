package com.gnivc.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TaskInfo {
    private UUID driver;
    private UUID transport;
    private UUID id;
    private Point locationStart;
    private Point locationEnd;
    private List<CargoDetails> cargos;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CargoDetails {
        private UUID cargoId;
        private int count;
    }
}
