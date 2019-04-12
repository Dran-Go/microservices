package com.github.drango.microservices.gateway.controller;

import com.github.drango.microservices.common.result.ResultVo;
import com.github.drango.microservices.gateway.service.LoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    private static final Logger LOG = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private LoginService loginService;

    @GetMapping(value = "/api/user/login")
    public ResultVo<Boolean> login(@RequestParam(name = "username") String username,
                                   @RequestParam(name = "password") String password) {
        Boolean success = false;
        try {
            success = loginService.verifyUser(username, password);
        } catch (Exception e){
            e.printStackTrace();
            LOG.error(e.getMessage());
        }
        return success ? new ResultVo<>(true) :
                new ResultVo<>(HttpStatus.BAD_REQUEST.value(), "用户不存在或密码错误");
    }
}
