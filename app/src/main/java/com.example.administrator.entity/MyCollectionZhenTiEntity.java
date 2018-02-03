package com.example.administrator.entity;

import java.io.Serializable;
import java.util.ArrayList;

public class MyCollectionZhenTiEntity implements Serializable {
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

    public ArrayList<MyCollectionZhenTiEntity.list> getList() {
        return list;
    }

    public void setList(ArrayList<MyCollectionZhenTiEntity.list> list) {
        this.list = list;
    }

    ArrayList<list>list;
    public class list{
        int id;
        int subjectid;
        String title;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getSubjectid() {
            return subjectid;
        }

        public void setSubjectid(int subjectid) {
            this.subjectid = subjectid;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
