package com.github.drango.microservices.gateway.router;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

@Component
public class UserRouter {
    @Bean
    public RouteLocator userRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                // query user
                .route(router -> router.path("/api/user").and().method(HttpMethod.GET)
                        .filters(filter -> filter.retry(2).hystrix(config -> config.setFallbackUri("forward:/fallback")))
                        .uri("lb://user-server"))
                // query all valid user
                .route(router -> router.path("/api/users").and().method(HttpMethod.GET)
                        .filters(filter -> filter.retry(2).hystrix(config -> config.setFallbackUri("forward:/fallback")))
                        .uri("lb://user-server"))
                // verify user email
                .route(router -> router.path("/api/user/email/verify").and().method(HttpMethod.GET)
                        .filters(filter -> filter.retry(2).hystrix(config -> config.setFallbackUri("forward:/fallback")))
                        .uri("lb://user-server"))
                // create user email verification
                .route(router -> router.path("/api/user/email/verify").and().method(HttpMethod.POST)
                        .filters(filter -> filter.retry(2).hystrix(config -> config.setFallbackUri("forward:/fallback")))
                        .uri("lb://user-server"))
                .build();
    }
}
