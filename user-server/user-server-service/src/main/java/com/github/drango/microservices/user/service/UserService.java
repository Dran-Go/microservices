package com.github.drango.microservices.user.service;

import com.github.drango.microservices.common.exception.BusinessException;
import com.github.drango.microservices.user.client.bean.request.UserRequest;
import com.github.drango.microservices.user.client.bean.response.UserBo;
import com.github.drango.microservices.user.client.bean.response.UserBriefBo;

import java.util.List;

public interface UserService {
    UserBo getUser(String username, String password) throws BusinessException;

    UserBo getUser(Integer userId) throws BusinessException;

    String getUserEmail(String username) throws BusinessException;

    List<UserBriefBo> getUserListData(Integer userId) throws BusinessException;

    UserBo createUser(UserRequest userRequest) throws BusinessException;

    UserBo modifyUser(Integer userId, UserRequest userRequest)throws BusinessException;
}
