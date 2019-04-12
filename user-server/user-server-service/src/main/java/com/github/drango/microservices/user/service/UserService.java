package com.github.drango.microservices.user.service;

import com.github.drango.microservices.user.client.bean.request.UserRequest;
import com.github.drango.microservices.user.client.bean.response.UserBo;

public interface UserService {
    UserBo getUser(String username, String password);

    UserBo getUser(Integer userId);

    UserBo addUser(UserRequest userRequest);

    UserBo modifyUser(Integer userId, UserRequest userRequest);
}
