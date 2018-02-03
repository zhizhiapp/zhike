package com.example.administrator.entity;

import java.io.Serializable;
import java.util.ArrayList;

public class HomeKnowledgeEntity implements Serializable {

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
    public ArrayList<HomeKnowledgeEntity.list> getList() {
        return list;
    }

    public void setList(ArrayList<HomeKnowledgeEntity.list> list) {
        this.list = list;
    }

    ArrayList<list> list = new ArrayList<>();

    public class list {
        int id;
        String name;
        String description;
        int frequency;
        String kid;
        int projectid;

        public int getCollstatus() {
            return collstatus;
        }

        public void setCollstatus(int collstatus) {
            this.collstatus = collstatus;
        }

        int collstatus;
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

        public int getFrequency() {
            return frequency;
        }

        public void setFrequency(int frequency) {
            this.frequency = frequency;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getKid() {
            return kid;
        }

        public void setKid(String kid) {
            this.kid = kid;
        }

        public int getProjectid() {
            return projectid;
        }

        public void setProjectid(int projectid) {
            this.projectid = projectid;
        }

        public String getThumb() {
            return thumb;
        }

        public void setThumb(String thumb) {
            this.thumb = thumb;
        }

        public int getWeight() {
            return weight;
        }

        public void setWeight(int weight) {
            this.weight = weight;
        }

        String thumb;
        int weight;
    }

}