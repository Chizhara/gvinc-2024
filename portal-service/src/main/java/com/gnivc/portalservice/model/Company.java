package com.gnivc.portalservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "companies", schema = "public")
public class Company {
    @Id
    @Column(name = "id")
    private UUID id;
    private String name;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "companies_users",
        joinColumns = {@JoinColumn(name = "company_id")},
        inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> users;
}
