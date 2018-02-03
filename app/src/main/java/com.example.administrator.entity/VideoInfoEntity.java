package com.example.administrator.entity;

import java.io.Serializable;

public class VideoInfoEntity implements Serializable {

    list list;

    public VideoInfoEntity.list getList() {
        return list;
    }

    public void setList(VideoInfoEntity.list list) {
        this.list = list;
    }

    public class list {
        int id = 0;
        String title = "";

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

        public int getChapterid() {
            return chapterid;
        }

        public void setChapterid(int chapterid) {
            this.chapterid = chapterid;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getTeacher_introd() {
            return teacher_introd;
        }

        public void setTeacher_introd(String teacher_introd) {
            this.teacher_introd = teacher_introd;
        }

        public String getCourse_introd() {
            return course_introd;
        }

        public void setCourse_introd(String course_introd) {
            this.course_introd = course_introd;
        }

        String url = "";
        int chapterid = 0;
        String description = "";
        String teacher_introd = "";
        String course_introd = "";
    }


}
