package com.example.administrator.entity;

import java.io.Serializable;

public class ShoppingCartEntity implements Serializable{


    int videoid;
    String title;
    String url;
    String thumb;
    int subjectid;

    public int getVideoid() {
        return videoid;
    }

    public void setVideoid(int videoid) {
        this.videoid = videoid;
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

    int chapterid;
    String price;
    int collstatus;

}
