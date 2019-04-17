package com.github.drango.microservices.gateway.service;

import com.github.drango.microservices.common.exception.BusinessException;
import com.github.drango.microservices.gateway.bean.request.UserRequest;

public interface UserService {
    Boolean createUser(UserRequest userRequest) throws BusinessException;
}
