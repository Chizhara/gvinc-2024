package com.gnivc.gatewayservice.service;

import com.gnivc.gatewayservice.model.AuthenticationImpl;
import jakarta.ws.rs.core.HttpHeaders;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.http.server.PathContainer;
import org.springframework.http.server.RequestPath;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

@Primary
@Component
@RequiredArgsConstructor
public class SecurityContextRepository implements ServerSecurityContextRepository {
    private final ApplicationSecurityProvider authenticationManager;

    @Override
    public Mono<Void> save(ServerWebExchange swe, SecurityContext sc) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Mono<SecurityContext> load(ServerWebExchange swe) {
        ServerHttpRequest request = swe.getRequest();
        String token = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        Optional<String> companyId = extractGroupId(request.getPath());
        if (token == null) {
            return Mono.empty();
        }
        return Mono.just(companyId
                .map(s ->
                    new AuthenticationImpl(token, s)).orElseGet(() ->
                    new AuthenticationImpl(token)))
            .flatMap(this::getSecurityContext);
    }

    public Optional<String> extractGroupId(RequestPath requestPath) {
        List<PathContainer.Element> pathParts = requestPath.elements(); //TODO можно оптимизировать;
        for (int i = 0; i < pathParts.size() - 1; i++) {
            if (pathParts.get(i).value().equals("company")) {
                return Optional.of(pathParts.get(i + 2).value());
            }
        }
        return Optional.empty();
    }

    private Mono<? extends SecurityContext> getSecurityContext(Authentication auth) {
        Mono<? extends SecurityContext> context = authenticationManager.authenticate(auth).map(SecurityContextImpl::new);
        return context;
    }
}
