package com.example.administrator.entity;

import java.io.Serializable;
import java.util.ArrayList;

public class SoluListEntity implements Serializable {
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

    public ArrayList<SoluListEntity.list> getList() {
        return list;
    }

    public void setList(ArrayList<SoluListEntity.list> list) {
        this.list = list;
    }

    String msg;
    ArrayList<list> list;

    public class list {
        int id;
        int pid;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getPid() {
            return pid;
        }

        public void setPid(int pid) {
            this.pid = pid;
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

        public int getWeight() {
            return weight;
        }

        public void setWeight(int weight) {
            this.weight = weight;
        }

        String name;
        String description;
        int weight;
    }
}
