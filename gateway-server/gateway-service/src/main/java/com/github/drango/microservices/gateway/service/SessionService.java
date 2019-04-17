package com.github.drango.microservices.gateway.service;

import com.github.drango.microservices.common.exception.BusinessException;
import com.github.drango.microservices.gateway.bean.vo.UserSessionVo;
import com.github.drango.microservices.user.client.bean.response.UserBo;

public interface SessionService {
    String createUserSession(String username, String password) throws BusinessException;

    UserSessionVo getUserSession(String sessionId);

    void updateUserSession(String sessionId, UserBo userBo) throws BusinessException;
}
