package com.gnivc.portalservice.model.dadata;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DaDataCompanySearch {
    private String query;
    private String type;

}
