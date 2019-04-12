package com.github.drango.microservices.user.client.api;

import com.github.drango.microservices.common.result.ResultBo;
import com.github.drango.microservices.user.client.bean.request.UserRequest;
import com.github.drango.microservices.user.client.bean.response.UserBo;
import com.github.drango.microservices.user.client.hystrix.UserApiHystrix;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.*;

@Primary
@FeignClient(name = "user-server", fallback = UserApiHystrix.class)
public interface UserApi {
    @GetMapping(value = "api/user")
    ResultBo<UserBo> getUser(@RequestHeader(name = "userId", required = false) Integer userId,
                             @RequestParam(name = "username", required = false) String username,
                             @RequestParam(name = "password", required = false) String password);

    @PostMapping(value = "api/user")
    ResultBo<UserBo> addUser(@RequestBody UserRequest userRequest);

    @PutMapping(value = "api/user")
    ResultBo<UserBo> modifyUser(@RequestHeader(name = "userId") Integer userId,
                                @RequestBody UserRequest userRequest);
}
