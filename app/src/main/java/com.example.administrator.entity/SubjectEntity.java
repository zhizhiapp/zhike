package com.example.administrator.entity;

import java.io.Serializable;
import java.util.ArrayList;

public class SubjectEntity implements Serializable {

    String code="";
    String msg="";
    ArrayList<listEntity> list=new ArrayList<>();
    public String getMsg() {
        return msg;
    }

    public ArrayList<listEntity> getList() {
        return list;
    }

    public void setList(ArrayList<listEntity> list) {
        this.list = list;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public class listEntity {
        int id=0;
        String name="";
        int projectid=0;

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

        public int getProjectid() {
            return projectid;
        }

        public void setProjectid(int projectid) {
            this.projectid = projectid;
        }


    }


}
