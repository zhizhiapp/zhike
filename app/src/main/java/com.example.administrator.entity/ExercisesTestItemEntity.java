package com.example.administrator.entity;

import java.io.Serializable;
import java.util.ArrayList;

public class ExercisesTestItemEntity implements Serializable {
    private String code;
    private String msg;
    private ArrayList<list> list;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public ArrayList<ExercisesTestItemEntity.list> getList() {
        return list;
    }

    public void setList(ArrayList<ExercisesTestItemEntity.list> list) {
        this.list = list;
    }

    public void setMsg(String msg) {
        this.msg = msg;

    }


    public class list {
        private int  id;
        private int pid;

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

        private String name;
        private String description;

    }
}
