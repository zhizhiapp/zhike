package com.example.administrator.entity;

import java.io.Serializable;

public class RegisterEntity implements Serializable{
    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public RegisterEntity.list getList() {
        return list;
    }

    public void setList(RegisterEntity.list list) {
        this.list = list;
    }

    String code="";
    String msg="";
    list list=new list();

    public class list{
        String id="";
        String mobile="";

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }
    }
}
