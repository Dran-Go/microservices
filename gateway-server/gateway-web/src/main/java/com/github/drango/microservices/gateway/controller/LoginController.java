package com.github.drango.microservices.gateway.controller;

import com.github.drango.microservices.common.exception.BusinessException;
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
    public ResultVo<String> login(@RequestParam(name = "username") String username,
                                  @RequestParam(name = "password") String password) {
        String sessionId = null;
        try {
            sessionId = loginService.createUserSession(username, password);
        } catch (BusinessException e) {
            LOG.error("login failed, username:{}, password:{}, response:{}", username, password, e.getMessage());
            return new ResultVo<>(e.getCode(), e.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            LOG.error("login failed, exception message:{}", e.getMessage());
        }
        return sessionId != null ? new ResultVo<>(sessionId) :
                new ResultVo<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统繁忙");
    }
}
