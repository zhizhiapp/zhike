package com.example.administrator.entity;

import java.io.Serializable;
import java.util.ArrayList;

public class GetSignConfigEntity implements Serializable{
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

    public ArrayList<GetSignConfigEntity.list> getList() {
        return list;
    }

    public void setList(ArrayList<GetSignConfigEntity.list> list) {
        this.list = list;
    }

    ArrayList<list>list;
    public class list{
        int id;

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

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getThumb() {
            return thumb;
        }

        public void setThumb(String thumb) {
            this.thumb = thumb;
        }

        String name;
        int number;
        String description;
        String thumb;

    }


}
