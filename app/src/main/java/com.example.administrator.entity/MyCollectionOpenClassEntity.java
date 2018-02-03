package com.example.administrator.entity;

import java.io.Serializable;
import java.util.ArrayList;

public class MyCollectionOpenClassEntity implements Serializable {
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

    public ArrayList<MyCollectionOpenClassEntity.list> getList() {
        return list;
    }

    public void setList(ArrayList<MyCollectionOpenClassEntity.list> list) {
        this.list = list;
    }

    String msg;
    ArrayList<list>list;
    public class list{
        int id;
        int video_id;
        String teacher;
        String head_img;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getVideo_id() {
            return video_id;
        }

        public void setVideo_id(int video_id) {
            this.video_id = video_id;
        }

        public String getTeacher() {
            return teacher;
        }

        public void setTeacher(String teacher) {
            this.teacher = teacher;
        }

        public String getHead_img() {
            return head_img;
        }

        public void setHead_img(String head_img) {
            this.head_img = head_img;
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

        String title;
        String url;
    }
}
