package com.github.drango.microservices.gateway.service;

import com.github.drango.microservices.common.exception.BusinessException;
import com.github.drango.microservices.gateway.bean.vo.UserSessionVo;

public interface LoginService {
    String createUserSession(String username, String password) throws BusinessException;

    UserSessionVo getUserSession(String sessionId);
}
