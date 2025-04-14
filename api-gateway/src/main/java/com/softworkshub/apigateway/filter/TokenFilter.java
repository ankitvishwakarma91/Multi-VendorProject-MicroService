package com.softworkshub.apigateway.filter;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class TokenFilter implements GlobalFilter, Ordered {


    private static final String secret = "6d044ae3d024f19e1f64c3bd572ed411116523caf7ddfb7dda16e73a5172804676f1d9350098f151dd680fbdd267c8ef1e5b72150c0d97c1e43c90fa3ac1d906";

//    public TokenFilter() {
//        super(Config.class);
//    }

//    @Override
//    public GatewayFilter apply(Config config) {
//        return (exchange, chain) -> {
//            String path = exchange.getRequest().getURI().getPath();
//
//            if (path.equals("/api/v1/auth/login") || path.equals("/api/v1/auth/register")
//                    || path.equals("/api/v1/product/all")
//            ) {
//                return chain.filter(exchange);
//            }
//
//            HttpHeaders headers = exchange.getRequest().getHeaders();
//            if (!headers.containsKey(HttpHeaders.AUTHORIZATION)) {
//                throw new RuntimeException("Authorization header not present");
//            }
//
//            String authHeader = headers.getFirst(HttpHeaders.AUTHORIZATION);
//
//            if (!authHeader.startsWith("Bearer ")) {
//                throw new RuntimeException("Authorization header not present");
//            }
//
//            String token = authHeader.substring(7);
//
//            try {
//                Claims claims = Jwts
//                        .parser()
//                        .setSigningKey(secret)
//                        .parseClaimsJws(token)
//                        .getBody();
//
//                String role = claims.get("roles", String.class);
//                String email = claims.get("email", String.class);
//                String name = claims.get("name", String.class);
//
//                System.out.println("Extracted Role: " + role);
//                // Add headers to forward
//                ServerHttpRequest mutatedRequest = exchange.getRequest().mutate()
//                        .header("X-User-Role", role)
//                        .header("X-User-Email", email)
//                        .header("X-User-Name", name)
//                        .build();
//                return chain.filter(exchange.mutate().request(mutatedRequest).build());
//            }catch (Exception e) {
//                throw new RuntimeException("Invalid token");
//            }
//        };
//    }


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getPath().toString();

        if (path.equals("/api/v1/auth/login") || path.equals("/api/v1/auth/register")
                || path.equals("/api/v1/product/all") || path.equals("/api/v1/order/test")
        ) {
            return chain.filter(exchange);
        }

        HttpHeaders headers = exchange.getRequest().getHeaders();
        if (!headers.containsKey(HttpHeaders.AUTHORIZATION)) {
            throw new RuntimeException("Authorization header missing");
        }

        String authHeader = headers.getFirst(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new RuntimeException("Invalid Authorization header");
        }

        String token = authHeader.substring(7);

        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();

            String role = claims.get("roles", String.class);
            String email = claims.get("email", String.class);
            String name = claims.get("name", String.class);

            System.out.println("roles : " + role);

            ServerHttpRequest mutated = exchange.getRequest().mutate()
                    .header("X-User-Role", role)
                    .header("X-User-Email", email)
                    .header("X-User-Name", name)
                    .build();

            return chain.filter(exchange.mutate().request(mutated).build());

        } catch (Exception e) {
            throw new RuntimeException("Invalid token");
        }
    }


//    public static class Config{
//
//    }

    @Override
    public int getOrder() {
        return 1;
    }

}
