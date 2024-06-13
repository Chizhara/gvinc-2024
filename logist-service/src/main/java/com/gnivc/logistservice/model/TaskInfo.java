package com.gnivc.logistservice.model;

import lombok.Data;
import org.postgresql.geometric.PGpoint;

import java.util.List;
import java.util.UUID;

@Data
public class TaskInfo {
    private final UUID id;
    private PGpoint locationStart;
    private PGpoint locationEnd;
    private List<CargoEnhancedInfo> cargos;

    @Data
    public static class CargoEnhancedInfo {
        private CargoInfo cargo;
        private int count;
    }
}
