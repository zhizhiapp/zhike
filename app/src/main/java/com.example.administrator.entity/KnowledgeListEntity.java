package com.example.administrator.entity;

import java.io.Serializable;
import java.util.ArrayList;

public class KnowledgeListEntity implements Serializable {

    String code;

    public ArrayList<KnowledgeListEntity.list> getList() {
        return list;
    }

    public void setList(ArrayList<KnowledgeListEntity.list> list) {
        this.list = list;
    }

    public String getMsg() {
        return msg;
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

    String msg;
    ArrayList<list>list;
    public class list{
        int id;
        int pid;
        String name;
        String description;
        int collstatus;

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

        public int getCollstatus() {
            return collstatus;
        }

        public void setCollstatus(int collstatus) {
            this.collstatus = collstatus;
        }

        public int getKnownum() {
            return knownum;
        }

        public void setKnownum(int knownum) {
            this.knownum = knownum;
        }

        int knownum;
    }


}
