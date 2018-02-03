package com.example.administrator.entity;

import java.io.Serializable;
import java.util.ArrayList;

public class MyCollectionExerciseEntity implements Serializable {

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

    public ArrayList<MyCollectionExerciseEntity.list> getList() {
        return list;
    }

    public void setList(ArrayList<MyCollectionExerciseEntity.list> list) {
        this.list = list;
    }

    ArrayList<list>list;
    public class list{
        int id;
        String name;

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

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        String description;
    }

}
