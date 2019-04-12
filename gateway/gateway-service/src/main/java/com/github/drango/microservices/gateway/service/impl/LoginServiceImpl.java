package com.github.drango.microservices.gateway.service.impl;

import com.github.drango.microservices.common.exception.BusinessException;
import com.github.drango.microservices.common.result.ResultBo;
import com.github.drango.microservices.gateway.common.CacheKeys;
import com.github.drango.microservices.gateway.service.LoginService;
import com.github.drango.microservices.user.client.api.UserApi;
import com.github.drango.microservices.user.client.bean.response.UserBo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class LoginServiceImpl implements LoginService {
    private static final Logger LOG = LoggerFactory.getLogger(LoginServiceImpl.class);

    @Autowired
    private UserApi userApi;

    @Autowired
    private RedisTemplate redisTemplate;


    @Override
    public Boolean verifyUser(String username, String password) throws BusinessException {

        ResultBo<UserBo> userResponse =  userApi.getUser(0, username, password);
        if (userResponse == null) {
            LOG.error("login failed ,username:{}, password:{}, response:null", username, password);
            return false;
        }

        if (userResponse.getCode() != HttpStatus.OK.value()) {
            throw new BusinessException(userResponse.getCode(), userResponse.getMessage());
        }

        Integer userId = userResponse.getData().getUserId();

        String sessionId = UUID.randomUUID().toString();
        String key = CacheKeys.SESSION_KEY_PREFIX + sessionId;
        ValueOperations<String, Integer> operation = redisTemplate.opsForValue();
        operation.set(key, userId, 60 , TimeUnit.SECONDS);

        return true;
    }
}
