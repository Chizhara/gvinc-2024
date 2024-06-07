package com.gnivc.gatewayservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gnivc.gatewayservice.model.UserDetailsEnhanced;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class UserDataForwardFilter implements GlobalFilter {
    private final ObjectMapper objectMapper;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        return ReactiveSecurityContextHolder.getContext()
            .filter(context -> context.getAuthentication() != null)
            .flatMap(context -> {
                UserDetailsEnhanced userDetails = (UserDetailsEnhanced) context.getAuthentication().getDetails();
                ServerHttpRequest request = getModifiedRequest(exchange, userDetails);
                return chain.filter(exchange.mutate().request(request).build());
            }).switchIfEmpty(chain.filter(exchange));
    }

    private ServerHttpRequest getModifiedRequest(ServerWebExchange exchange, UserDetailsEnhanced userDetails) {
        try {
            return exchange.getRequest().mutate()
                .header("x-username", userDetails.getUsername())
                .header("x-userId", userDetails.getUserId())
                .header("x-roles", objectMapper.writeValueAsString(userDetails.getRoles()))
                .build();
        } catch (JsonProcessingException e) {
            throw new RuntimeException();
        }
    }
}
