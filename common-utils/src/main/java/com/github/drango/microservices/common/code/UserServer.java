package com.github.drango.microservices.common.code;

public enum UserServer {
    ERROR_PASSWORD(1000, "密码错误"),
    ERROR_USERNAME(1001, "账号不存在"),
    REPEAT_USERNAME(1002, "用户名已被使用"),
    INVALID_EMAIL(1003, "邮箱未验证"),
    REPEAT_EMAIL(1004, "邮箱已被使用"),
    ;

    private int code;
    private String message;

    UserServer() {}

    UserServer(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
