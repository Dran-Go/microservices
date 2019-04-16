package com.github.drango.microservices.user.client.hystrix;

import com.github.drango.microservices.common.result.ResultBo;
import com.github.drango.microservices.common.result.ResultListBo;
import com.github.drango.microservices.user.client.api.UserApi;
import com.github.drango.microservices.user.client.bean.request.UserRequest;
import com.github.drango.microservices.user.client.bean.response.UserBo;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class UserApiHystrix implements UserApi {
    @Override
    public ResultBo<UserBo> getUser(Integer userId, String username, String password) {
        return new ResultBo<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统繁忙");
    }

    @Override
    public ResultListBo<UserBo> getAllUser(Integer userId) {
        return new ResultListBo<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统繁忙");
    }

    @Override
    public ResultBo<String> getUserEmail(String username) {
        return new ResultBo<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统繁忙");
    }

    @Override
    public ResultBo<String> createUser(UserRequest userRequest) {
        return new ResultBo<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统繁忙");
    }

    @Override
    public ResultBo<UserBo> modifyUser(Integer userId, UserRequest userRequest) {
        return new ResultBo<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统繁忙");
    }

    @Override
    public ResultBo<String> createEmailVerification(Integer userId) {
        return new ResultBo<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统繁忙");
    }

    @Override
    public ResultBo<Boolean> checkEmailVerification(String code) {
        return new ResultBo<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统繁忙");
    }
}
