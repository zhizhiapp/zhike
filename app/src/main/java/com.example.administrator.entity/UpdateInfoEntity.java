package com.example.administrator.entity;

import java.io.Serializable;

public class UpdateInfoEntity implements Serializable{

    String code;

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

    public UpdateInfoEntity.list getList() {
        return list;
    }

    public void setList(UpdateInfoEntity.list list) {
        this.list = list;
    }

    String msg;
    list list;
    public class list{
        String email;
        String colleges;

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }

        String major;
        String nickname;
        int sex;

        public String getColleges() {
            return colleges;
        }

        public void setColleges(String colleges) {
            this.colleges = colleges;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getMajor() {
            return major;
        }

        public void setMajor(String major) {
            this.major = major;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }


        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        String address;
    }

}
