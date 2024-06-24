package com.gnivc.logistservice.model.task;

import com.gnivc.model.Point;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class TaskCreateRequest {
    private Point locationStart;
    private Point locationEnd;
    private UUID driver;
    private UUID transport;
    private List<CargoDetails> cargoDetails;

    @Data
    public static class CargoDetails {
        private UUID id;
        private int count;
    }
}
