package com.example.administrator.entity;

import java.io.Serializable;
import java.util.ArrayList;

public class VideoEntity implements Serializable {

    String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public ArrayList<VideoList> getList() {
        return list;
    }

    public void setList(ArrayList<VideoList> list) {
        this.list = list;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    String msg;
    ArrayList<VideoList> list;

    public class VideoList implements Serializable {
        int id = 0;
        String title = "";
        String url = "";

        public int getCartstatus() {
            return cartstatus;
        }

        public void setCartstatus(int cartstatus) {
            this.cartstatus = cartstatus;
        }

        String thumb = "";
        String price = "";
        int cartstatus=0;
        public int getCollstatus() {
            return collstatus;
        }

        public void setCollstatus(int collstatus) {
            this.collstatus = collstatus;
        }

        int chapterid = 0;
        int collstatus=0;
        double times = 0;

        public double getTimes() {
            return times;
        }

        public void setTimes(double times) {
            this.times = times;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getThumb() {
            return thumb;
        }

        public void setThumb(String thumb) {
            this.thumb = thumb;
        }

        public int getChapterid() {
            return chapterid;
        }

        public void setChapterid(int chapterid) {
            this.chapterid = chapterid;
        }


    }
}
