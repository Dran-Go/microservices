package com.github.drango.microservices.user.service;

import com.github.drango.microservices.common.exception.BusinessException;
import com.github.drango.microservices.user.client.bean.request.UserRequest;
import com.github.drango.microservices.user.client.bean.response.UserBo;

public interface UserService {
    UserBo getUser(String username, String password) throws BusinessException;

    UserBo getUser(Integer userId) throws BusinessException;

    UserBo createUser(UserRequest userRequest);

    UserBo modifyUser(Integer userId, UserRequest userRequest);
}
