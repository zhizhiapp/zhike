package com.example.administrator.entity;

import java.io.Serializable;
import java.util.ArrayList;

public class HomeMicroClassEntity implements Serializable {


    public ArrayList<HomeMicroClassEntity.list> getList() {
        return list;
    }

    public void setList(ArrayList<HomeMicroClassEntity.list> list) {
        this.list = list;
    }

    ArrayList<list>list=new ArrayList<>();
    public class list {
        int id=0;
        String title="";
        String url="";

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

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getThumb() {
            return thumb;
        }

        public void setThumb(String thumb) {
            this.thumb = thumb;
        }

        public int getSubjectid() {
            return subjectid;
        }

        public void setSubjectid(int subjectid) {
            this.subjectid = subjectid;
        }

        public int getChapterid() {
            return chapterid;
        }

        public void setChapterid(int chapterid) {
            this.chapterid = chapterid;
        }

        String thumb="";
        int subjectid=0;
        int chapterid=0;
        String price;

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public int getCollstatus() {
            return collstatus;
        }

        public void setCollstatus(int collstatus) {
            this.collstatus = collstatus;
        }

        public int getCartstatus() {
            return cartstatus;
        }

        public void setCartstatus(int cartstatus) {
            this.cartstatus = cartstatus;
        }

        int collstatus=0;
        int cartstatus=0;

    }
}
