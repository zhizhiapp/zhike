package com.example.administrator.entity;

import java.io.Serializable;
import java.util.ArrayList;

public class MyCollectionIntelligenceEntity implements Serializable{
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

    public ArrayList<MyCollectionIntelligenceEntity.list> getList() {
        return list;
    }

    public void setList(ArrayList<MyCollectionIntelligenceEntity.list> list) {
        this.list = list;
    }

    String msg;
    ArrayList<list>list;
    public class list{
        int id;

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

        String title;
    }
}
