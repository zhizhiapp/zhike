package com.example.administrator.entity;

import java.io.Serializable;
import java.util.ArrayList;

public class HomeExercisesEntity implements Serializable {

    ArrayList<list> list = new ArrayList<>();

    public ArrayList<HomeExercisesEntity.list> getList() {
        return list;
    }

    public void setList(ArrayList<HomeExercisesEntity.list> list) {
        this.list = list;
    }

    public class list {
        int id = 0;
        String name = "";
        int collstatus;

        public int getCollstatus() {
            return collstatus;
        }

        public void setCollstatus(int collstatus) {
            this.collstatus = collstatus;
        }

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

        public int getFrequency() {
            return frequency;
        }

        public void setFrequency(int frequency) {
            this.frequency = frequency;
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

        String description = "";
        int frequency = 0;
        String kid = "";
        int projectid = 0;
    }
}
