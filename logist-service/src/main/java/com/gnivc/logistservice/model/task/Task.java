package com.gnivc.logistservice.model.task;

import com.gnivc.logistservice.model.cargo.TaskCargo;
import com.gnivc.logistservice.model.driver.Driver;
import com.gnivc.logistservice.model.transport.Transport;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tasks", schema = "public")
public class Task {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name = "start_point_lat")
    private Float locationStartLat;
    @Column(name = "start_point_lon")
    private Float locationStartLon;
    @Column(name = "end_point_lat")
    private Float locationEndLat;
    @Column(name = "end_point_lon")
    private Float locationEndLon;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "driver_id")
    private Driver driver;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transport_id")
    private Transport transport;
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id")
    private List<TaskCargo> cargos;

}
