package com.github.drango.microservices.gateway.service;

import com.github.drango.microservices.common.exception.BusinessException;
import com.github.drango.microservices.gateway.bean.request.ModifyUserRequest;
import com.github.drango.microservices.gateway.bean.request.UserRequest;
import com.github.drango.microservices.user.client.bean.response.UserBo;

public interface UserService {
    Boolean createUser(UserRequest userRequest) throws BusinessException;

    UserBo modifyUser(Integer userId, ModifyUserRequest modifyUserRequest) throws BusinessException;
}
