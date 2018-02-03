package com.example.administrator.entity;

import java.io.Serializable;

public class GetSignEntity implements Serializable {

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

    public GetSignEntity.list getList() {
        return list;
    }

    public void setList(GetSignEntity.list list) {
        this.list = list;
    }

    list list;
    public class list{
        int signStatus;
        int signNumber;
        int total_integral;

        public int getSignStatus() {
            return signStatus;
        }

        public void setSignStatus(int signStatus) {
            this.signStatus = signStatus;
        }

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

        public int getIscomplete() {
            return iscomplete;
        }

        public void setIscomplete(int iscomplete) {
            this.iscomplete = iscomplete;
        }

        int iscomplete;
    }


}
