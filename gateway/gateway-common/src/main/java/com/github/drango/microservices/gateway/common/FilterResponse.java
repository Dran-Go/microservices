package com.github.drango.microservices.gateway.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

public class FilterResponse {
    private static final Logger LOG = LoggerFactory.getLogger(FilterResponse.class);

    private static final String ERROR_RESPONSE = "{\"code\":%d,\"message\":\"%s\"}";

    public static Mono<Void> write(ServerWebExchange exchange, int code, String message) {
        ServerHttpResponse response = exchange.getResponse();

        String data = String.format(ERROR_RESPONSE, code, message);
        byte[] bytes = data.getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = response.bufferFactory().wrap(bytes);

        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
        LOG.error("request filter error:{}", data);

        return response.writeWith(Mono.just(buffer));
    }
}
