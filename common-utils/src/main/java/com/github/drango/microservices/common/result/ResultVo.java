package com.github.drango.microservices.common.result;

import java.io.Serializable;

public class ResultVo<T> implements Serializable {
    private int code;
    private String message;
    private T data;

    public ResultVo() {
        this.code = 200;
        this.message = "ok";
    }

    public ResultVo(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public ResultVo(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public ResultVo(T data) {
        this.code = 200;
        this.message = "ok";
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
