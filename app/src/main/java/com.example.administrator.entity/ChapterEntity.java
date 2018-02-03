package com.example.administrator.entity;

import java.io.Serializable;
import java.util.ArrayList;

public class ChapterEntity implements Serializable {

    String code = "";
    String msg = "";

    public ArrayList<ChapterEntity.list> getList() {
        return list;
    }

    public void setList(ArrayList<ChapterEntity.list> list) {
        this.list = list;
    }

    ArrayList<list> list;

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


    public class list {
        int id = 0;
        String name = "";
        String number = "";

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }


    }


}
