package com.gnivc.driverservice.model.route;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "route_points", schema = "public")
public class RoutePoint {
    @Id
    @Column(name = "id")
    private UUID id;
    @ManyToOne
    @JoinColumn(name = "route_id")
    private Route route;
    private Float lat;
    private Float lon;

}
