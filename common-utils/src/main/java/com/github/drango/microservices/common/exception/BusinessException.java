package com.github.drango.microservices.common.exception;

public class BusinessException extends RuntimeException{
    private Integer code;

    public Integer getCode() {
        return this.code;
    }

    public BusinessException(String message) {
        super(message);
        this.code = 1000;
    }

    public BusinessException(Integer code, String message) {
        super(message);
        this.code = code;
    }
}
