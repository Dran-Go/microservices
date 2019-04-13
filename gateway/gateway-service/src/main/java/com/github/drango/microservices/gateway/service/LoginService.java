package com.github.drango.microservices.gateway.service;

import com.github.drango.microservices.common.exception.BusinessException;

public interface LoginService {
    String createUserSession(String username, String password) throws BusinessException;
}
