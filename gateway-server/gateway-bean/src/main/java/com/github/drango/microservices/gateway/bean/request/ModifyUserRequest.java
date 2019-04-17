package com.github.drango.microservices.gateway.bean.request;

import java.io.Serializable;

public class ModifyUserRequest implements Serializable {
    private String password;
    private String email;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
