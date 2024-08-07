package com.interbank.challenge.config.jwt;

import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;
import reactor.util.context.Context;

import java.util.List;

@Component
public class JwtFilter implements WebFilter {

    private final JwtUtil jwtUtil;
    private final ReactiveUserDetailsService userDetailsService;

    public JwtFilter(JwtUtil jwtUtil, ReactiveUserDetailsService userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();

        final List<String> authorizationHeader = request.getHeaders().get(HttpHeaders.AUTHORIZATION);

        if (authorizationHeader != null && !authorizationHeader.isEmpty() && authorizationHeader.get(0).startsWith("Bearer ")) {
            String jwt = authorizationHeader.get(0).substring(7);
            String username = jwtUtil.extractUsername(jwt);

            if (username != null) {
                return userDetailsService.findByUsername(username)
                        .flatMap(userDetails -> {
                            if (jwtUtil.validateToken(jwt, userDetails.getUsername())) {
                                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                                        userDetails, null, userDetails.getAuthorities());
                                SecurityContext securityContext = new SecurityContextImpl(authentication);

                                return chain.filter(exchange)
                                        .contextWrite(Context.of(SecurityContext.class, securityContext));
                            } else {
                                return chain.filter(exchange);
                            }
                        });
            }
        }
        return chain.filter(exchange);
    }
}