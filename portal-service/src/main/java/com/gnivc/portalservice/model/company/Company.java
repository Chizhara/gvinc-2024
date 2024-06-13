package com.gnivc.portalservice.model.company;

import com.gnivc.portalservice.model.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "companies", schema = "public")
public class Company {
    @Id
    @Column(name = "id")
    private String id;
    private String name;
    private Long inn;
    private String address;
    private Long kpp;
    private Long ogrn;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "companies_users",
        joinColumns = {@JoinColumn(name = "company_id")},
        inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> users;
}
