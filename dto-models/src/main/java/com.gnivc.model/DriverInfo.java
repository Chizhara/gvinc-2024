package com.gnivc.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DriverInfo implements Serializable {

    private UUID userId;
    private UserInfo userInfo;
    private AddCompanyInfo addCompany;
    private String removeCompany;

    public DriverInfo(UUID userId, UserInfo userInfo, AddCompanyInfo addCompany) {
        this.userId = userId;
        this.userInfo = userInfo;
        this.addCompany = addCompany;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UserInfo {
        private String username;
        private String name;
        private String surname;
        private String email;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AddCompanyInfo {
        private UUID companyId;
    }
}
