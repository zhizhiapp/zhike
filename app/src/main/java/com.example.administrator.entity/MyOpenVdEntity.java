package com.example.administrator.entity;

import java.io.Serializable;
import java.util.ArrayList;

public class MyOpenVdEntity implements Serializable {

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

    public ArrayList<MyOpenVdEntity.list> getList() {
        return list;
    }

    public void setList(ArrayList<MyOpenVdEntity.list> list) {
        this.list = list;
    }

    ArrayList<list> list;

    public class list {
        int id;
        String title;
        String url;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
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

        public String getThumb() {
            return thumb;
        }

        public void setThumb(String thumb) {
            this.thumb = thumb;
        }

        String thumb;
    }
}
