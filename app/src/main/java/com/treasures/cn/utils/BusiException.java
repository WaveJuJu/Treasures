package com.treasures.cn.utils;

public class BusiException extends Exception{
    private int code;
    private String msg;

    public BusiException() {
        code = 999;
        msg = "unexpect exception";
    }

    public BusiException(String msg){
        code = 999;
        this.msg = msg;
    }

    public BusiException(int code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public BusiException(int code, String msg, Throwable cause) {
        super(msg, cause);
        this.code = code;
        this.msg = msg;
    }

    public BusiException(String msg, Throwable cause){
        super(cause);
        code = 999;
        this.msg = msg;
    }

    public BusiException(Throwable cause) {
        super(cause);
        code = 999;
        msg = "unexpect exception";
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
