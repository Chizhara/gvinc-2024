package com.gnivc.logistservice.kafka;

import com.gnivc.logistservice.service.DriverService;
import com.gnivc.logistservice.service.RouteEventService;
import com.gnivc.logistservice.service.TransportService;
import com.gnivc.model.DriverInfo;
import com.gnivc.model.RouteEventInfo;
import com.gnivc.model.TransportInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaAppListener {
    private final DriverService driverService;
    private final TransportService transportService;
    private final RouteEventService routeEventService;

    @KafkaListener(topics = {"portal.driver.info", "logist.driver.info.buf"}, groupId = "logist",
        containerFactory = "driverKafkaListenerContainerFactory")
    public void listenDrivers(DriverInfo driverInfo) {
        driverService.addDriver(driverInfo);
    }

    @KafkaListener(topics = {"portal.transport.info", "logist.transport.info.buf"}, groupId = "logist",
        containerFactory = "transportKafkaListenerContainerFactory")
    public void listenTransport(TransportInfo transportInfo) {
        transportService.addTransport(transportInfo);
    }

    @KafkaListener(topics = "driver.route.event.info", groupId = "logist",
        containerFactory = "routeEventKafkaListenerContainerFactory")
    public void listenRouteEvent(RouteEventInfo routeEventInfo) {
        routeEventService.addRouteEvent(routeEventInfo);
    }
}
