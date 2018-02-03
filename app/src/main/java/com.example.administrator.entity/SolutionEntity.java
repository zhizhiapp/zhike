package com.example.administrator.entity;

import java.io.Serializable;

public class SolutionEntity implements Serializable {

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

    public SolutionEntity.list getList() {
        return list;
    }

    public void setList(SolutionEntity.list list) {
        this.list = list;
    }

    list list;

    public class list {
        float accuracy;
        float mastery;

        public float getMastery() {
            return mastery;
        }

        public void setMastery(float mastery) {
            this.mastery = mastery;
        }

        public float getAccuracy() {
            return accuracy;
        }

        public void setAccuracy(float accuracy) {
            this.accuracy = accuracy;
        }

        public String getTrendcont() {
            return trendcont;
        }

        public void setTrendcont(String trendcont) {
            this.trendcont = trendcont;
        }

        public String getVerse() {
            return verse;
        }

        public void setVerse(String verse) {
            this.verse = verse;
        }

        String trendcont = "";
        String verse = "";
    }

}
