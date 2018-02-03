package com.example.administrator.entity;

import java.io.Serializable;

public class HomeMyhightscoreEntity implements Serializable {
    String code = "";
    String msg = "";

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

    public HomeMyhightscoreEntity.list getList() {
        return list;
    }

    public void setList(HomeMyhightscoreEntity.list list) {
        this.list = list;
    }

    list list;

    public class list implements Serializable {
        int id = 0;
        String headimgurl = "";
        String nickname = "";

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getHeadimgurl() {
            return headimgurl;
        }

        public void setHeadimgurl(String headimgurl) {
            this.headimgurl = headimgurl;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public float getMastery_rate() {
            return mastery_rate;
        }

        public void setMastery_rate(float mastery_rate) {
            this.mastery_rate = mastery_rate;
        }

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }

        float mastery_rate = 0;
        int number = 0;
    }

}
