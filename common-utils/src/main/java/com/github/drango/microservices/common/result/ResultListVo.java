package com.github.drango.microservices.common.result;

import java.io.Serializable;
import java.util.List;

public class ResultListVo<T> implements Serializable {
    private int code;
    private Integer total;
    private String message;
    private List<T> listData;

    public ResultListVo() {
        this.code = 200;
        this.message = "ok";
    }

    public ResultListVo(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public ResultListVo(int total, List<T> listData) {
        this.code = 200;
        this.total = total;
        this.message = "ok";
        this.listData = listData;
    }

    public ResultListVo(List<T> listData) {
        this.code = 200;
        this.message = "ok";
        this.listData = listData;
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

    public void setTotal(int total) {
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
