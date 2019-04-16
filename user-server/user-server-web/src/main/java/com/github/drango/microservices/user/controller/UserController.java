package com.github.drango.microservices.user.controller;

import com.github.drango.microservices.common.exception.BusinessException;
import com.github.drango.microservices.common.result.ResultBo;
import com.github.drango.microservices.common.result.ResultListBo;
import com.github.drango.microservices.user.client.bean.request.UserRequest;
import com.github.drango.microservices.user.client.bean.response.UserBo;
import com.github.drango.microservices.user.client.bean.response.UserBriefBo;
import com.github.drango.microservices.user.service.EmailService;
import com.github.drango.microservices.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {
    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;
    @Autowired
    private EmailService emailService;

    @GetMapping(value = "/api/user")
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
        } catch (BusinessException e) {
            LOG.error("query user failed, userId:{}, username:{}, password:{}, error:{}",
                    userId, username, password, e.getMessage());
            return new ResultBo<>(e.getCode(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            LOG.error("query user failed, userId:{}, username:{}, password:{}",
                    userId, username, password);
        }
        return userBo != null ? new ResultBo<>(userBo) :
                new ResultBo<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统繁忙");
    }

    @GetMapping(value = "/api/users")
    public ResultListBo<UserBriefBo> getAllUser() {
        List<UserBriefBo> listUser = null;
        try {
            listUser = userService.getUserListData();
        } catch (BusinessException e) {
            LOG.error("get all user failed, error:{}", e.getMessage());
            return new ResultListBo<>(e.getCode(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            LOG.error("get all user failed");
        }
        return listUser != null ? new ResultListBo<>(listUser.size(), listUser) :
                new ResultListBo<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统繁忙");
    }

    @GetMapping(value = "/api/user/email")
    public ResultBo<String> getUserEmail(@RequestParam(name = "username") String username) {
        String email = null;
        try {
            email = userService.getUserEmail(username);
        } catch (BusinessException e) {
            LOG.error("get user email failed, username:{}, error:{}", username, e.getMessage());
            return new ResultBo<>(e.getCode(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            LOG.error("get user email failed");
        }
        return email != null ? new ResultBo<>(email) :
                new ResultBo<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统繁忙");
    }

    @PostMapping(value = "/api/user")
    public ResultBo<String> createUser(@RequestBody UserRequest userRequest) {
        String verifyUri = null;
        try {
            UserBo userBo =  userService.createUser(userRequest);
            verifyUri = emailService.createEmailVerifyUri(userBo.getUserId());
        } catch (BusinessException e) {
            LOG.error("create user failed, request:{}, error:{}", userRequest, e.getMessage());
            return new ResultBo<>(e.getCode(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            LOG.error("create user failed, request:{}", userRequest.toString());
        }
        return verifyUri != null ? new ResultBo<>(verifyUri) :
                new ResultBo<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统繁忙");
    }

    @PutMapping(value = "/api/user")
    public ResultBo<UserBo> modifyUser(@RequestHeader(name = "userId") Integer userId,
                                       @RequestBody UserRequest userRequest) {
        UserBo userBo = null;
        try {
            userBo = userService.modifyUser(userId, userRequest);
        } catch (BusinessException e) {
            LOG.error("modify user failed, userId:{}, request:{}, error:{}",
                    userId, userRequest, e.getMessage());
            return new ResultBo<>(e.getCode(), e.getMessage());
        }  catch (Exception e) {
            e.printStackTrace();
            LOG.error("modify user failed, userId:{}, request:{}",
                    userId, userRequest.toString());
        }
        return userBo != null ? new ResultBo<>(userBo) :
                new ResultBo<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统繁忙");
    }
}
