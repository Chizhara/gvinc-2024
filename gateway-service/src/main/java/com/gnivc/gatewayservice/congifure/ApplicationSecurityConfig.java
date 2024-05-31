package com.gnivc.gatewayservice.congifure;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@RequiredArgsConstructor
@EnableWebFluxSecurity
public class ApplicationSecurityConfig {

    private final ApplicationSecurityProvider provider;
    private final SecurityContextRepository contextRepository;

    @Bean
    public SecurityWebFilterChain configure(ServerHttpSecurity http) {
        return http.httpBasic(Customizer.withDefaults())
            .csrf(ServerHttpSecurity.CsrfSpec::disable)
            .cors(Customizer.withDefaults())
            .securityContextRepository(contextRepository)
            .headers(headerSpec ->
                headerSpec.contentSecurityPolicy(contentSecurityPolicySpec ->
                    contentSecurityPolicySpec.policyDirectives("upgrade-insecure-requests")))
            .authenticationManager(provider)
            .authorizeExchange(requests -> {
                requests.pathMatchers("/actuator/**").permitAll();
                requests.pathMatchers("/openid-connect/**").permitAll();
                requests.pathMatchers("/portal/company/**").authenticated();
                requests.pathMatchers("/portal/company").hasRole("REGISTRATOR");
                requests.pathMatchers("/portal/registrator").permitAll();
            }).build();
    }


}
