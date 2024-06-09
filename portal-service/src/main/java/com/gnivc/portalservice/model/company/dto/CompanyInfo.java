package com.gnivc.portalservice.model.company.dto;

import lombok.Data;

@Data
public class CompanyInfo {
    private String id;
    private String name;
    private Long inn;
    private String address;
    private Long kpp;
    private Long ogrn;
    private Integer driversCount;
    private Integer logistsCount;
}
