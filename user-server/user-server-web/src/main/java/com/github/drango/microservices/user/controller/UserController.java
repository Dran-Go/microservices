package com.github.drango.microservices.user.controller;

import com.github.drango.microservices.common.result.ResultBo;
import com.github.drango.microservices.user.client.bean.request.UserRequest;
import com.github.drango.microservices.user.client.bean.response.UserBo;
import com.github.drango.microservices.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @GetMapping(value = "api/user")
    public ResultBo<UserBo> getUser(@RequestHeader(name = "userId", required = false) Integer userId,
                                    @RequestParam(name = "username", required = false) String username,
                                    @RequestParam(name = "password", required = false) String password) {
        UserBo userBo = null;
        try {
            if (userId != null && userId != 0) {
                userBo = userService.getUser(userId);
            } else {
                userBo = userService.getUser(username, password);
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOG.error("query user error, userId:{}, username:{}, password:{}", userId, username, password);
        }
        return userBo != null ? new ResultBo<>(userBo) :
                new ResultBo<>(HttpStatus.NOT_FOUND.value(), "用户不存在或密码错误");
    }

    @PostMapping(value = "api/user")
    public ResultBo<UserBo> addUser(@RequestBody UserRequest userRequest) {
        UserBo userBo = null;
        try {
            userBo =  userService.addUser(userRequest);
        } catch (Exception e) {
            e.printStackTrace();
            LOG.error("create user error, request:{}", userRequest.toString());
        }
        return userBo != null ? new ResultBo<>(userBo) :
                new ResultBo<>(HttpStatus.BAD_REQUEST.value(), "创建用户失败");
    }

    @PutMapping(value = "api/user")
    public ResultBo<UserBo> modifyUser(@RequestHeader(name = "userId") Integer userId,
                                       @RequestBody UserRequest userRequest) {
        UserBo userBo = null;
        try {
            userBo = userService.modifyUser(userId, userRequest);
        } catch (Exception e) {
            e.printStackTrace();
            LOG.error("modify user error, userId:{}, request:{}", userId, userRequest.toString());
        }
        return userBo != null ? new ResultBo<>(userBo) :
                new ResultBo<>(HttpStatus.BAD_REQUEST.value(), "修改用户信息失败");
    }
}
