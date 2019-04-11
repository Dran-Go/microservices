package com.github.drango.microservices.gateway.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author DranGo
 * @time 2019/4/10 10:19
 */

@RestController
public class LoginController {
    private static final Logger LOG = LoggerFactory.getLogger(LoginController.class);

    @GetMapping(value = "/api/v1/login")
    public String login() {
        return "Hello World";
    }
}
