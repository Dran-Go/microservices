package com.github.drango.microservices.gateway.bean.request;

import java.io.Serializable;

public class LetterMailRequest implements Serializable {
    private String username;
    private String toUsername;
    private String text;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getToUsername() {
        return toUsername;
    }

    public void setToUsername(String toUsername) {
        this.toUsername = toUsername;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
