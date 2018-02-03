package com.example.administrator.entity;

import java.io.Serializable;
import java.util.ArrayList;

public class LearningSituationEntity implements Serializable {

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

    public ArrayList<LearningSituationEntity.list> getList() {
        return list;
    }

    public void setList(ArrayList<LearningSituationEntity.list> list) {
        this.list = list;
    }

    ArrayList<list>list;
    public class list {
        int id;
        String name;
        int projectid;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getProjectid() {
            return projectid;
        }

        public void setProjectid(int projectid) {
            this.projectid = projectid;
        }

        public float getMastery_rate() {
            return mastery_rate;
        }

        public void setMastery_rate(float mastery_rate) {
            this.mastery_rate = mastery_rate;
        }

        float mastery_rate;

    }


}
