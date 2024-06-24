package com.gnivc.logistservice.model.task;

import com.gnivc.logistservice.model.driver.DriverInfo;
import com.gnivc.logistservice.model.transport.TransportInfo;
import com.gnivc.model.Point;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class TaskInfo {
    private final Point startPoint;
    private final Point endPoint;
    private final DriverInfo driver;
    private final TransportInfo transport;
    private final List<CargoDetails> cargos;

    @Data
    public static class CargoDetails {
        private UUID id;
        private String name;
        private String count;
    }
}
