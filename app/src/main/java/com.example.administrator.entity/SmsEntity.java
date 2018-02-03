package com.example.administrator.entity;

import java.io.Serializable;

public class SmsEntity implements Serializable{

    String code="";
    String msg="";

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
