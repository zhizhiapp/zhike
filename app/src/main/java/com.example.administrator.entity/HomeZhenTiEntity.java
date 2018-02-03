package com.example.administrator.entity;

import java.io.Serializable;
import java.util.ArrayList;

public class HomeZhenTiEntity implements Serializable {
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

    public ArrayList<HomeZhenTiEntity.list> getList() {
        return list;
    }

    public void setList(ArrayList<HomeZhenTiEntity.list> list) {
        this.list = list;
    }

    ArrayList<list> list = new ArrayList<>();

    public class list {
        int id;


        String title;
        int subjectid;

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

        public int getSubjectid() {
            return subjectid;
        }

        public void setSubjectid(int subjectid) {
            this.subjectid = subjectid;
        }

        public int getCollstatus() {
            return collstatus;
        }

        public void setCollstatus(int collstatus) {
            this.collstatus = collstatus;
        }

        String thumb;
        int collstatus;
    }


}