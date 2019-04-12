package com.github.drango.microservices.gateway.service.impl;

import com.github.drango.microservices.common.result.ResultBo;
import com.github.drango.microservices.gateway.service.LoginService;
import com.github.drango.microservices.user.client.api.UserApi;
import com.github.drango.microservices.user.client.bean.response.UserBo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {
    private static final Logger LOG = LoggerFactory.getLogger(LoginServiceImpl.class);

    @Autowired
    private UserApi userApi;

    @Override
    public Boolean verifyUser(String username, String password) {

        ResultBo<UserBo> userResponse =  userApi.getUser(0, username, password);
        if (userResponse == null || userResponse.getCode() != HttpStatus.OK.value()) {
            LOG.error("login failed , username:{}, password:{}", username, password);
            return false;
        }
        UserBo userBo = userResponse.getData();
        // TODO 2019/04/11 :写入session
        return userBo.getUserId() != null;
    }
}
