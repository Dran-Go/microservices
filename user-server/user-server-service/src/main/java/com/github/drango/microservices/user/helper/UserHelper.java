package com.github.drango.microservices.user.helper;

import com.github.drango.microservices.user.client.bean.response.UserBo;
import com.github.drango.microservices.user.client.bean.response.UserBriefBo;
import com.github.drango.microservices.user.domain.User;
import org.springframework.stereotype.Component;

@Component
public class UserHelper {
    public UserBo convert(User user) {
        if (user == null) {
            return null;
        }
        UserBo userBo = new UserBo();
        userBo.setUserId(user.getId());
        userBo.setUsername(user.getUsername());
        userBo.setEmail(user.getEmail());
        userBo.setCreateTime(user.getCreateTime());
        userBo.setUpdateTime(user.getUpdateTime());
        return userBo;
    }

    public UserBriefBo convertBrief(User user) {
        if (user == null) {
            return null;
        }
        UserBriefBo userBriefBo = new UserBriefBo();
        userBriefBo.setUserId(user.getId());
        userBriefBo.setUsername(user.getUsername());
        userBriefBo.setCreateTime(user.getCreateTime());
        return userBriefBo;
    }
}
