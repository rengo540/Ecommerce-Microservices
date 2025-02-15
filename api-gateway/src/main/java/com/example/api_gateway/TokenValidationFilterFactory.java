package com.example.api_gateway;

import com.example.api_gateway.security.jwt.JwtUtils;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class TokenValidationFilterFactory extends AbstractGatewayFilterFactory<TokenValidationFilterFactory.Config> {
    @Autowired
    private JwtUtils jwtUtils;

    public TokenValidationFilterFactory() {
        super(Config.class); // Required
    }

    @Override
    public GatewayFilter apply(TokenValidationFilterFactory.Config config) {
        return new TokenValidationFilter(jwtUtils);
    }

    public static class Config {
        
    }

    private static class TokenValidationFilter implements GatewayFilter, Ordered {

        @Value("${auth.token.jwtkey}")
        private String SECRET_KEY; // Change to a strong key!
        private static final String AUTHORIZATION_HEADER = "Authorization";
        private static final String BEARER_PREFIX = "Bearer ";
        private final JwtUtils jwtUtils;

        private TokenValidationFilter(JwtUtils jwtUtils) {
            this.jwtUtils = jwtUtils;
        }

        @Override
        public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
            HttpHeaders headers = exchange.getRequest().getHeaders();
            if (!headers.containsKey(AUTHORIZATION_HEADER)) {
                log.warn("Missing Authorization Header");
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }

            String token = headers.getFirst(AUTHORIZATION_HEADER);
            if (token == null || !token.startsWith(BEARER_PREFIX)) {
                log.warn("Invalid Authorization Header Format");
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }

            try {
                // Remove "Bearer " prefix
                token = token.substring(BEARER_PREFIX.length());
                Claims claims = jwtUtils.validateToken(token);

                log.info("Token is valid. User ID: {}", claims.getSubject());

                // Forward request with user details if needed
                exchange.getRequest().mutate()
                        .header("userId", claims.getSubject())
                        .build();

                return chain.filter(exchange);

            } catch (Exception e) {
                log.error("Token validation failed: {}", e.getMessage());
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }
        }

        @Override
        public int getOrder() {
            return -1;
        }
    }
}