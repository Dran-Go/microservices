package com.github.drango.microservices.user.service.impl;

import com.github.drango.microservices.common.code.UserServer;
import com.github.drango.microservices.common.exception.BusinessException;
import com.github.drango.microservices.user.client.bean.request.UserRequest;
import com.github.drango.microservices.user.client.bean.response.UserBo;
import com.github.drango.microservices.user.client.bean.response.UserBriefBo;
import com.github.drango.microservices.user.dao.UserDao;
import com.github.drango.microservices.user.domain.User;
import com.github.drango.microservices.user.helper.UserHelper;
import com.github.drango.microservices.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger LOG = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserHelper userHelper;

    @Override
    public UserBo getUser(String username, String password) throws BusinessException {
        User user = this.getUserCommon(null, username);
        if (!password.equals(user.getPassword())) {
            throw new BusinessException(
                    UserServer.ERROR_PASSWORD.getCode(), UserServer.ERROR_PASSWORD.getMessage());
        }
        return userHelper.convert(user);
    }

    @Override
    public UserBo getUser(Integer userId) throws BusinessException {
        User user = this.getUserCommon(userId, null);
        return userHelper.convert(user);
    }

    @Override
    public String getUserEmail(String username) throws BusinessException {
        User user = this.getUserCommon(null, username);
        return user.getEmail();
    }

    @Override
    public List<UserBriefBo> getUserListData() throws BusinessException {
        List<UserBriefBo> listUserBo = new ArrayList<>();

        List<User> listUser = userDao.findByEmailValid(true);
        if (listUser != null && listUser.size() > 0) {
            listUser.forEach(user -> listUserBo.add(userHelper.convertBrief(user)));
        }

        return listUserBo;
    }

    @Override
    public UserBo createUser(UserRequest userRequest) throws BusinessException{
        if (userRequest == null) {
            throw new BusinessException(HttpStatus.BAD_REQUEST.value() ,"请求参数错误");
        }
        String username = userRequest.getUsername();
        String email = userRequest.getEmail();
        if (userDao.findByUsername(username) != null) {
            throw new BusinessException(UserServer.REPEAT_USERNAME.getCode(), UserServer.REPEAT_USERNAME.getMessage());
        }
        if (userDao.findByEmail(email) != null) {
            throw new BusinessException(UserServer.REPEAT_EMAIL.getCode(), UserServer.REPEAT_EMAIL.getMessage());
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(userRequest.getPassword());
        user.setEmail(email);
        user.setEmailValid(false);

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
            throw new BusinessException(HttpStatus.BAD_REQUEST.value() ,"请求参数错误");
        }

        User user = new User();
        user.setId(userId);
        user.setPassword(userRequest.getPassword());
        if (userRequest.getEmail() != null) {
            user.setEmail(userRequest.getEmail());
            user.setEmailValid(false);
        }

        if (userDao.update(user) == 1) {
            return userHelper.convert(user);
        } else {
            LOG.debug("modify userId:{} failed", userId);
            return null;
        }
    }

    private User getUserCommon(Integer userId, String username) throws BusinessException {
        User user = null;
        if (userId != null && userId > 0) {
            user = userDao.findById(userId);
        } else if (username != null) {
            user = userDao.findByUsername(username);
        } else {
            throw new BusinessException(HttpStatus.BAD_REQUEST.value(), "请求参数错误");
        }

        if (user == null) {
            throw new BusinessException(
                    UserServer.ERROR_USERNAME.getCode(), UserServer.ERROR_USERNAME.getMessage());
        } else if (!user.getEmailValid()) {
            throw new BusinessException(
                    UserServer.INVALID_EMAIL.getCode(), UserServer.INVALID_EMAIL.getMessage());
        }

        return user;
    }
}
