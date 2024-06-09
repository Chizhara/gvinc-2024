package com.gnivc.gatewayservice.congifure;

import com.gnivc.gatewayservice.model.Role;
import com.gnivc.gatewayservice.service.ApplicationSecurityProvider;
import com.gnivc.gatewayservice.service.SecurityContextRepository;
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
                requests.pathMatchers("/portal/registrator").permitAll();
                requests.pathMatchers("/openid-connect/**").permitAll();
                requests.pathMatchers("/portal/company/**").authenticated();
                requests.pathMatchers("/portal/company/{companyId}/user/admin").hasRole("ADMIN");
                requests.pathMatchers("/portal/company/{companyId}/user/logist").hasRole("LOGIST");
                requests.pathMatchers("/portal/company/{companyId}/user/driver").hasAnyRole("ADMIN", "LOGIST");
                requests.pathMatchers("/portal/company/{companyId}/transport").hasAnyRole("ADMIN", "LOGIST");
                requests.pathMatchers("/portal/company").hasRole("REGISTRATOR");
            }).build();
    }

}
