package com.github.drango.microservices.gateway.controller;

import com.github.drango.microservices.common.exception.BusinessException;
import com.github.drango.microservices.common.result.ResultVo;
import com.github.drango.microservices.gateway.bean.request.UserRequest;
import com.github.drango.microservices.gateway.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @PostMapping("api/user")
    public ResultVo<Boolean> createUser(@RequestBody UserRequest userRequest) {
        Boolean success = false;
        try {
            success = userService.createUser(userRequest);
        } catch (BusinessException e) {
            LOG.error("create user failed, ");
            return new ResultVo<>(e.getCode(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            LOG.error("create user failed, exception message:{}", e.getMessage());
        }
        return success != null ? new ResultVo<>(success) :
                new ResultVo<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统繁忙");
    }



}
