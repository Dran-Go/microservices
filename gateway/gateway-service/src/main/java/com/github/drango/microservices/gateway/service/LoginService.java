package com.github.drango.microservices.gateway.service;

public interface LoginService {
    Boolean verifyUser(String username, String password);
}
