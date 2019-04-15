package com.github.drango.microservices.user.service;

import com.github.drango.microservices.common.exception.BusinessException;
import com.github.drango.microservices.user.client.bean.request.UserRequest;
import com.github.drango.microservices.user.client.bean.response.UserBo;

import java.util.List;

public interface UserService {
    UserBo getUser(String username, String password) throws BusinessException;

    UserBo getUser(Integer userId) throws BusinessException;

    String getUserEmail(String username) throws BusinessException;

    List<UserBo> getUserListData() throws BusinessException;

    UserBo createUser(UserRequest userRequest) throws BusinessException;

    UserBo modifyUser(Integer userId, UserRequest userRequest)throws BusinessException;
}
