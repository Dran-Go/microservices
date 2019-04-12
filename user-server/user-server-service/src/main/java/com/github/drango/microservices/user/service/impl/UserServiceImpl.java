package com.github.drango.microservices.user.service.impl;

import com.github.drango.microservices.user.client.bean.request.UserRequest;
import com.github.drango.microservices.user.client.bean.response.UserBo;
import com.github.drango.microservices.user.dao.UserDao;
import com.github.drango.microservices.user.domain.User;
import com.github.drango.microservices.user.helper.UserHelper;
import com.github.drango.microservices.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger LOG = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserHelper userHelper;

    @Override
    public UserBo getUser(String username, String password) {
        User user = null;
        user = userDao.findByUsername(username);

        if (user != null && password.equals(user.getPassword())) {
            return userHelper.convert(user);
        } else {
            return null;
        }
    }

    @Override
    public UserBo getUser(Integer userId) {
        if (userId == null || userId <= 0) {
            return null;
        }
        User user = null;
        user = userDao.findById(userId);
        return userHelper.convert(user);
    }

    @Override
    public UserBo addUser(UserRequest userRequest) {
        if (userRequest == null) {
            return null;
        }
        User user = new User();
        user.setUsername(userRequest.getUsername());
        user.setPassword(userRequest.getPassword());
        user.setEmail(userRequest.getEmail());

        if(userDao.addUser(user)) {
            return userHelper.convert(user);
        } else {
            LOG.debug("create user fail");
            return null;
        }
    }

    @Override
    public UserBo modifyUser(Integer userId, UserRequest userRequest) {
        if (userId == null || userId <= 0) {
            return null;
        }

        User user = new User();
        user.setId(userId);
        user.setPassword(userRequest.getPassword());
        user.setEmail(userRequest.getEmail());

        if (userDao.updateUser(user) == 1) {
            return userHelper.convert(user);
        } else {
            LOG.debug("modify userId:{} fail", userId);
            return null;
        }
    }
}
