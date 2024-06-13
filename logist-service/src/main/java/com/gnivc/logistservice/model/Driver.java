package com.gnivc.logistservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
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
@Table(name = "drivers", schema = "public")
public class Driver {
    @Id
    @Column(name = "id")
    private UUID id;
    private String username;
    private String name;
    private String surname;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "drivers_companies",
        joinColumns = {@JoinColumn(name = "driver_id")},
        inverseJoinColumns = @JoinColumn(name = "company_id"))
    private List<Company> companies;
}
