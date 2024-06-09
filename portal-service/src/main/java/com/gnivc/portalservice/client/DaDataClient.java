package com.gnivc.portalservice.client;

import com.gnivc.portalservice.model.dadata.DaDataCompanySearch;
import com.gnivc.portalservice.model.dadata.DaDataSuggestionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DaDataClient {

    @Value("${dadata.base-url}")
    private String baseUrl;
    @Value("${dadata.token}")
    private String token;
    @Value("${dadata.company-get-path}")
    private String companyGetPath;
    private final WebClient webClient;

    public DaDataClient() {
        this.webClient = WebClient.builder().build();
    }

    public Optional<DaDataSuggestionResponse> findByInn(DaDataCompanySearch daDataCompanyRequest) {
        ResponseEntity<DaDataSuggestionResponse> response = webClient.post()
            .uri(companyGetPath)
            .header("Authorization", "Token " + token)
            .bodyValue(daDataCompanyRequest)
            .retrieve()
            .toEntity(DaDataSuggestionResponse.class)
            .block();

        return response != null ? Optional.of(response.getBody()) : Optional.empty();
    }
}
