package com.github.drango.microservices.common.result;

import java.io.Serializable;
import java.util.List;

public class ResultListTo<T> implements Serializable {
    private int code;
    private Integer total;
    private String message;
    private List<T> listData;

    public ResultListTo() {
        this.code = 200;
        this.message = "ok";
    }

    public ResultListTo(Integer total, List<T> listData) {
        this.code = 200;
        this.message = "ok";
        this.total = total;
        this.listData = listData;
    }

    public ResultListTo(List<T> listData) {
        this.code = 200;
        this.message = "ok";
        this.listData = listData;
    }

    public ResultListTo(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<T> getListData() {
        return listData;
    }

    public void setListData(List<T> listData) {
        this.listData = listData;
    }
}
