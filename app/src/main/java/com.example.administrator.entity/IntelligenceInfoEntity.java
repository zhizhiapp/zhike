package com.example.administrator.entity;

import java.io.Serializable;

public class IntelligenceInfoEntity implements Serializable {

    String code;
    String msg;

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

    public IntelligenceInfoEntity.list getList() {
        return list;
    }

    public void setList(IntelligenceInfoEntity.list list) {
        this.list = list;
    }

    list list;

    public class list {
        int id;
        String title;
        String description;
        String keywords;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getCreatetime() {
            return createtime;
        }

        public void setCreatetime(int createtime) {
            this.createtime = createtime;
        }

        public int getFictitious_hits() {
            return fictitious_hits;
        }

        public void setFictitious_hits(int fictitious_hits) {
            this.fictitious_hits = fictitious_hits;
        }

        public String getSourcesite() {
            return sourcesite;
        }

        public void setSourcesite(String sourcesite) {
            this.sourcesite = sourcesite;
        }

        public String getKeywords() {
            return keywords;
        }

        public void setKeywords(String keywords) {
            this.keywords = keywords;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        String sourcesite;
        int fictitious_hits;
        String content;
        int createtime;

    }


}
