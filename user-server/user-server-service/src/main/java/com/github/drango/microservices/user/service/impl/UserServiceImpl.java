package com.github.drango.microservices.user.service.impl;

import com.github.drango.microservices.common.exception.BusinessException;
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
    public UserBo getUser(String username, String password) throws BusinessException {
        User user = null;
        user = userDao.findByUsername(username);

        if (user == null) {
            throw new BusinessException(1001, "用户不存在");
        } else if (!password.equals(user.getPassword())) {
            throw new BusinessException(1002, "密码错误");
        } else if (!user.getEmailValid()) {
            throw new BusinessException(1003, "邮箱未验证");
        }

        return userHelper.convert(user);
    }

    @Override
    public UserBo getUser(Integer userId) throws BusinessException {
        if (userId == null || userId <= 0) {
            throw new BusinessException(400 ,"请求参数错误");
        }

        User user = null;
        user = userDao.findById(userId);
        if (user == null) {
            throw new BusinessException(1001, "用户不存在");
        } else if (!user.getEmailValid()) {
            throw new BusinessException(1003, "邮箱未验证");
        }

        return userHelper.convert(user);
    }

    @Override
    public UserBo createUser(UserRequest userRequest) {
        if (userRequest == null) {
            return null;
        }
        User user = new User();
        user.setUsername(userRequest.getUsername());
        user.setPassword(userRequest.getPassword());
        user.setEmail(userRequest.getEmail());

        if(userDao.add(user)) {
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

        if (userDao.update(user) == 1) {
            return userHelper.convert(user);
        } else {
            LOG.debug("modify userId:{} fail", userId);
            return null;
        }
    }
}
