package com.gnivc.portalservice.model.company;

import com.gnivc.portalservice.model.user.User;
import com.gnivc.portalservice.model.user.UserRole;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "companies_users", schema = "public")
public class CompanyEmployer {
    @EmbeddedId
    private CompanyUserId ids;
    @Enumerated(EnumType.STRING)
    private UserRole role;

    public CompanyEmployer(String userId, String companyId, UserRole userRole) {
        this.ids = new CompanyUserId(userId, companyId);
        this.role = userRole;
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Embeddable
    public static class CompanyUserId {

        @Column(name = "user_id")
        private String userId;

        @Column(name = "company_id")
        private String companyId;
    }
}
