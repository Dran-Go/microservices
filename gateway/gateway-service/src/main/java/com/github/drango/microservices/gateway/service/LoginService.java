package com.github.drango.microservices.gateway.service;

import com.github.drango.microservices.common.exception.BusinessException;

public interface LoginService {
    Boolean verifyUser(String username, String password) throws BusinessException;
}
