package com.example.administrator.entity;

import java.io.Serializable;

public class AddIntegralEntity implements Serializable{
    String code;
    String msg;

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

    public AddIntegralEntity.list getList() {
        return list;
    }

    public void setList(AddIntegralEntity.list list) {
        this.list = list;
    }

    list list;
    public class list{
        int signNumber;
        int total_integral;

        public int getSignNumber() {
            return signNumber;
        }

        public void setSignNumber(int signNumber) {
            this.signNumber = signNumber;
        }

        public int getTotal_integral() {
            return total_integral;
        }

        public void setTotal_integral(int total_integral) {
            this.total_integral = total_integral;
        }
    }



}
