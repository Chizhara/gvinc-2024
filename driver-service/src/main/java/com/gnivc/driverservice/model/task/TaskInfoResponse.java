package com.gnivc.driverservice.model.task;

import com.gnivc.driverservice.model.transport.TransportInfoReponse;
import com.gnivc.model.Point;
import com.gnivc.model.TransportInfo;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class TaskInfoResponse {
    private final UUID id;
    private final Point startPoint;
    private final Point endPoint;
    private final TransportInfoReponse transport;
    private final List<CargoDetails> cargoDetails;

    @Data
    @Builder
    public static class CargoDetails {
        private UUID id;
        private UUID name;
        private long count;
    }
}
