package com.mur.exception;

import java.io.Serializable;

public class ExceptionInfo implements Serializable{
    
    private String msg;
    
    private String exception;
    
    private String path;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
