package com.github.drango.microservices.gateway.bean.vo;

import java.io.Serializable;

public class UserSessionVo implements Serializable {
    private Integer userId;
    private String username;
    private String email;

////    Redis正则化
//    @Override
//    public String toString() {
//        final StringBuilder sb = new StringBuilder("UserSessionVo{");
//        sb.append("userId=").append(userId);
//        sb.append(", username='").append(username).append('\'');
//        sb.append(", email='").append(email).append('\'');
//        sb.append('}');
//        return sb.toString();
//    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
