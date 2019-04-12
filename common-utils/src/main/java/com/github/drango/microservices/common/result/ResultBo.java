package com.github.drango.microservices.common.result;

import java.io.Serializable;

public class ResultBo<T> implements Serializable {

    private int code;
    private String message;
    private T data;

    public static ResultBo success() {
        ResultBo instance = new ResultBo();
        instance.setCode(200);
        instance.setMessage("ok");
        return instance;
    }

    public static <T> ResultBo success(T data) {
        ResultBo instance = new ResultBo();
        instance.setCode(200);
        instance.setMessage("ok");
        instance.setData(data);
        return instance;
    }


    public static ResultBo failed(String message) {
        ResultBo instance = new ResultBo();
        instance.setCode(1000);
        instance.setMessage(message);
        return instance;
    }


    public static ResultBo failed(int code, String message) {
        ResultBo instance = new ResultBo();
        instance.setCode(code);
        instance.setMessage(message);
        return instance;
    }

    public ResultBo() {
        this.code = 200;
        this.message = "ok";
    }

    public ResultBo(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public ResultBo(T data) {
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


//    @Override
//    public String toString() {
//        final StringBuilder sb = new StringBuilder("ResultBo{");
//        sb.append("code=").append(code);
//        sb.append(", message='").append(message).append('\'');
//        sb.append(", data=").append(data);
//        sb.append('}');
//        return sb.toString();
//    }
}
