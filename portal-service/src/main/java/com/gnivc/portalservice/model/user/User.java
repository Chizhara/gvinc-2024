package com.gnivc.portalservice.model.user;

import com.gnivc.portalservice.model.company.Company;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users", schema = "public")
public class User {
    @Id
    @Column(name = "id")
    private String id;
    private String username;
    private String name;
    private String surname;
    private String email;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "companies_users",
        joinColumns = {@JoinColumn(name = "user_id")},
        inverseJoinColumns = @JoinColumn(name = "company_id"))
    private List<Company> companies;
}
