package com.example.administrator.entity;

import java.io.Serializable;
import java.util.ArrayList;

public class MyhightscoreEntity implements Serializable {

    String code = "";

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

    public ArrayList<MyhightscoreEntity.list> getList() {
        return list;
    }

    public void setList(ArrayList<MyhightscoreEntity.list> list) {
        this.list = list;
    }

    String msg = "";
    ArrayList<list> list = new ArrayList<>();

    public class list {
        int id = 0;
        String nickname = "";
        String headimgurl = "";

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getHeadimgurl() {
            return headimgurl;
        }

        public void setHeadimgurl(String headimgurl) {
            this.headimgurl = headimgurl;
        }

        public String getRealname() {
            return realname;
        }

        public void setRealname(String realname) {
            this.realname = realname;
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

        String realname = "";
        float mastery_rate = 0;
        int number = 0;
    }
}
