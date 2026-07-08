package com.ecommerce.apigateway.security;

import com.ecommerce.apigateway.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class JwtAuthGlobalFilter implements GlobalFilter, Ordered {

    // пути, которым JWT не нужен — их пропускаем «как есть»
    private static final List<String> PUBLIC_PATHS = List.of(
            "/api/auth/register",
            "/api/auth/login",
            "/api/stock/products"
    );

    private final JwtService jwtService;

    public JwtAuthGlobalFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getPath().value();

        // 1) публичные эндпоинты — без проверки
        if (isPublic(path)) {
            return chain.filter(exchange);
        }

        // 2) достаём Bearer-токен
        String authHeader = exchange.getRequest().getHeaders().getFirst("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return unauthorized(exchange);
        }

        // 3) проверяем подпись и срок; на любой проблеме — 401, дальше не пускаем
        try {
            Claims claims = jwtService.parseClaims(authHeader.substring(7));
            String userId = claims.getSubject();
            String role = claims.get("role", String.class);

            // 4) срезаем возможную подделку от клиента и кладём доверенные значения
            ServerHttpRequest mutated = exchange.getRequest().mutate()
                    .headers(h -> {
                        h.remove("X-User-Id");
                        h.remove("X-User-Role");
                        h.add("X-User-Id", userId);
                        h.add("X-User-Role", role);
                    })
                    .build();

            return chain.filter(exchange.mutate().request(mutated).build());
        } catch (JwtException e) {
            return unauthorized(exchange);
        }
    }

    private boolean isPublic(String path) {
        if (!path.startsWith("/api/")) return true;
        return PUBLIC_PATHS.stream().anyMatch(path::startsWith);
    }

    private Mono<Void> unauthorized(ServerWebExchange exchange) {
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        return exchange.getResponse().setComplete();  // короткое замыкание: ответ без проксирования
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;  // авторизация раньше маршрутизации
    }
}