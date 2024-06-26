package com.gnivc.driverservice.controller;

import com.gnivc.driverservice.model.route.RouteEventRequest;
import com.gnivc.driverservice.model.route.RouteInfoResponse;
import com.gnivc.driverservice.model.route.RoutePointInfoResponse;
import com.gnivc.driverservice.model.route.RoutePointRequest;
import com.gnivc.driverservice.service.RouteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/driver")
public class RouteController {
    private final RouteService routeService;

    @PostMapping("/company/{companyId}/task/{taskId}/route")
    public RouteInfoResponse createRoute(@RequestHeader("x-userId") UUID driverId,
                                         @PathVariable("companyId") UUID companyId,
                                         @PathVariable("taskId") UUID taskId) {
        return routeService.createRoute(driverId, taskId, companyId);
    }

    @PostMapping("/company/{companyId}/route/{routeId}/event")
    public RouteInfoResponse createRouteEvent(@RequestHeader("x-userId") UUID driverId,
                                              @PathVariable("routeId") UUID routeId,
                                              @PathVariable("companyId") UUID companyId,
                                              @RequestBody RouteEventRequest routeEventRequest) {
        return routeService.addRouteEvent(routeEventRequest, routeId, driverId, companyId);
    }

    @PostMapping("/company/{companyId}/route/{routeId}/point")
    public RoutePointInfoResponse createRoutePoint(@RequestHeader("x-userId") UUID driverId,
                                                   @PathVariable("routeId") UUID routeId,
                                                   @RequestBody RoutePointRequest routeEventRequest) {
        return routeService.addRoutePoint(routeEventRequest, routeId, driverId);
    }
}
