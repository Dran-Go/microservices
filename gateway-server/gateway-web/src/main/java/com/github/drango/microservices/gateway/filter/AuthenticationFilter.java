package com.github.drango.microservices.gateway.filter;

import com.github.drango.microservices.gateway.bean.vo.UserSessionVo;
import com.github.drango.microservices.gateway.common.Constants;
import com.github.drango.microservices.gateway.common.FilterResponse;
import com.github.drango.microservices.gateway.common.UrlWhiteList;
import com.github.drango.microservices.gateway.service.SessionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Order(1)
@Component
public class AuthenticationFilter implements WebFilter {
    private static final Logger LOG = LoggerFactory.getLogger(AuthenticationFilter.class);

    @Autowired
    private SessionService sessionService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String uri = exchange.getRequest().getPath().pathWithinApplication().value();
        String methodValue = exchange.getRequest().getMethodValue();

        // outside the white list
        if (!UrlWhiteList.checkWhiteList(uri, methodValue)) {
            String sessionId = exchange.getRequest().getHeaders().getFirst(Constants.CLIENT_SESSION);
            UserSessionVo session = sessionService.getUserSession(sessionId);

            // unauthorized
            if (session == null ||session.getUserId() == null) {
                return FilterResponse.write(exchange, HttpStatus.UNAUTHORIZED.value(), Constants.UNAUTHORIZED_MESSAGE);
            }

            ServerHttpRequest serverHttpRequest = exchange.getRequest()
                    .mutate()
                    .headers(httpHeaders -> httpHeaders.remove("userId"))
                    .headers(httpHeaders -> httpHeaders.remove("username"))
                    .headers(httpHeaders -> httpHeaders.remove("email"))
                    .headers(httpHeaders -> httpHeaders.remove(Constants.CLIENT_SESSION))
                    .header("userId", String.valueOf(session.getUserId()))
                    .header("username", session.getUsername())
                    .header("email", session.getEmail())
                    .header("sessionId", sessionId)
                    .build();
            ServerWebExchange serverWebExchange = exchange.mutate()
                    .request(serverHttpRequest)
                    .build();
            return chain.filter(serverWebExchange);
        }

        return chain.filter(exchange);
    }
}
