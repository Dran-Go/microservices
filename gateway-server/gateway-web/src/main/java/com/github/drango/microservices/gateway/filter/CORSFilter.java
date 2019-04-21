package com.github.drango.microservices.gateway.filter;


import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Order(0)
@Component
public class CORSFilter implements WebFilter {
    private static final String ALL = "*";
    private static final String MAX_AGE = "18000L";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        HttpHeaders requestHeaders = request.getHeaders();
        HttpMethod requestMethod = requestHeaders.getAccessControlRequestMethod();

        HttpHeaders headers = response.getHeaders();

        headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, requestHeaders.getOrigin());
        headers.addAll(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, requestHeaders.getAccessControlRequestHeaders());
        if (requestMethod != null) {
            headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, requestMethod.name());
        }
        headers.add(HttpHeaders.ACCESS_CONTROL_REQUEST_HEADERS, ALL);
        headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS, "true");
        headers.add(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, ALL);
        headers.add(HttpHeaders.ACCESS_CONTROL_MAX_AGE, MAX_AGE);

        // 浏览器预执行
        if (request.getMethod() == HttpMethod.OPTIONS) {
            response.setStatusCode(HttpStatus.OK);
            return Mono.empty();
        }
        return chain.filter(exchange);
    }

}
