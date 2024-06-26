package com.gnivc.logistservice.model.route;

import com.gnivc.logistservice.model.company.Company;
import com.gnivc.logistservice.model.task.Task;
import com.gnivc.model.RouteEventType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "routes_events", schema = "public")
public class RouteEvent {
    @Id
    @Column(name = "id")
    private UUID id;
    private UUID routeId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id")
    private Task task;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private Company company;
    @Column(name = "time")
    private Instant timestamp;
    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private RouteEventType eventType;
}
