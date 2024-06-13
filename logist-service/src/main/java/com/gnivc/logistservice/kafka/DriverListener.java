package com.gnivc.logistservice.kafka;

import com.gnivc.logistservice.service.DriverService;
import com.gnivc.logistservice.service.TransportService;
import com.gnivc.model.DriverInfo;
import com.gnivc.model.TransportInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DriverListener {
    private final DriverService driverService;
    private final TransportService transportService;

    @KafkaListener(topics = "logist.driver.info", groupId = "logist")
    public void listenDrivers(DriverInfo driverInfo) {
        driverService.addDriver(driverInfo);
    }

    @KafkaListener(topics = "logist.transport.info", groupId = "logist")
    public void listenTranposrt(TransportInfo transportInfo) {
        transportService.addTransport(transportInfo);
    }
}
