package com.example.administrator.entity;

import java.io.Serializable;
import java.util.ArrayList;

public class ZhenTiListEntity implements Serializable {


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

    public ArrayList<ZhenTiListEntity.list> getList() {
        return list;
    }

    public void setList(ArrayList<ZhenTiListEntity.list> list) {
        this.list = list;
    }

    String msg;
    ArrayList<list> list;

    public class list {
        int collstatus;
        int id;

        public int getCollstatus() {
            return collstatus;
        }

        public void setCollstatus(int collstatus) {
            this.collstatus = collstatus;
        }

        String title;

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

        public int getSubjectid() {
            return subjectid;
        }

        public void setSubjectid(int subjectid) {
            this.subjectid = subjectid;
        }

        public String getThumb() {
            return thumb;
        }

        public void setThumb(String thumb) {
            this.thumb = thumb;
        }

        int subjectid;
        String thumb;
    }

}
