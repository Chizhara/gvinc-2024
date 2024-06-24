package com.gnivc.portalservice.model.transport;

import com.gnivc.portalservice.model.company.Company;
import com.gnivc.portalservice.model.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "transports", schema = "public")
public class Transport {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String vin;
    private String gosNumber;
    private LocalDate releaseDate;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "register_id")
    private User register;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "company_id")
    private Company company;
}
