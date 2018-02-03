package com.example.administrator.entity;

import java.io.Serializable;
import java.util.ArrayList;

public class SoluVideoEntity implements Serializable {

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

    public ArrayList<SoluVideoEntity.list> getList() {
        return list;
    }

    public void setList(ArrayList<SoluVideoEntity.list> list) {
        this.list = list;
    }

    ArrayList<list> list;

    public class list {
        int id;
        String title;

        public int getCartstatus() {
            return cartstatus;
        }

        public void setCartstatus(int cartstatus) {
            this.cartstatus = cartstatus;
        }

        int cartstatus=0;
        public int getCollstatus() {
            return collstatus;
        }

        public void setCollstatus(int collstatus) {
            this.collstatus = collstatus;
        }

        String url;
        String thumb;
        int collstatus;

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

        public int getChapterid() {
            return chapterid;
        }

        public void setChapterid(int chapterid) {
            this.chapterid = chapterid;
        }

        int subjectid;
        int chapterid;
    }


}
