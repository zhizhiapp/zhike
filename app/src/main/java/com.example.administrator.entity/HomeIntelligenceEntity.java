package com.example.administrator.entity;

import java.io.Serializable;
import java.util.ArrayList;

public class HomeIntelligenceEntity implements Serializable {

    private ArrayList<list> list;

    public ArrayList<HomeIntelligenceEntity.list> getList() {
        return list;
    }

    public void setList(ArrayList<HomeIntelligenceEntity.list> list) {
        this.list = list;
    }

    public class list {
        private int id;
        private String title;
        private String description;

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

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

        public String getThumb() {
            return thumb;
        }

        public void setThumb(String thumb) {
            this.thumb = thumb;
        }

        String thumb;


    }
}