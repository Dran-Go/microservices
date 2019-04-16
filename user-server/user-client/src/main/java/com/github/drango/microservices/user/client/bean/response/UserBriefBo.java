package com.github.drango.microservices.user.client.bean.response;

import java.io.Serializable;
import java.util.Date;

public class UserBriefBo implements Serializable {
    private Integer userId;
    private String username;
    private Date createTime;

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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
