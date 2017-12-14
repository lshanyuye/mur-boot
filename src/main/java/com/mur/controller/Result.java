package com.mur.controller;

public class Result {
    
    private Object data;
    
    private String msg;

    private Result(String msg, Object data) {
        this.data = data;
        this.msg = msg;
    }
    
    private Result(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static Result ok(String message) {
        return new Result(message);
    }
    public static Result ok(String message, Object data) {
        return new Result(message, data);
    }
}
