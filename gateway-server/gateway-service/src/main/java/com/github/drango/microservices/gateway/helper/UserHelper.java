package com.github.drango.microservices.gateway.helper;

import com.github.drango.microservices.gateway.bean.request.ModifyUserRequest;
import com.github.drango.microservices.user.client.bean.request.UserRequest;
import org.springframework.stereotype.Component;

@Component
public class UserHelper {
    public UserRequest convert(com.github.drango.microservices.gateway.bean.request.UserRequest userRequest) {
        UserRequest request = new UserRequest();
        request.setUsername(userRequest.getUsername());
        request.setPassword(userRequest.getPassword());
        request.setEmail(userRequest.getEmail());
        return request;
    }

    public UserRequest convert(ModifyUserRequest modifyUserRequest) {
        UserRequest request = new UserRequest();
        request.setPassword(modifyUserRequest.getPassword());
        request.setEmail(modifyUserRequest.getEmail());
        return request;
    }
}
