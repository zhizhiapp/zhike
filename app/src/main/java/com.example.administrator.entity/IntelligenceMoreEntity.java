package com.example.administrator.entity;

import java.io.Serializable;
import java.util.ArrayList;

public class IntelligenceMoreEntity implements Serializable {

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

    public ArrayList<IntelligenceMoreEntity.list> getList() {
        return list;
    }

    public void setList(ArrayList<IntelligenceMoreEntity.list> list) {
        this.list = list;
    }

    String msg;
    ArrayList<list> list;

    public class list {
        int id;
        String title;

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

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getThumb() {
            return thumb;
        }

        public void setThumb(String thumb) {
            this.thumb = thumb;
        }

        public int getCreatetime() {
            return createtime;
        }

        public void setCreatetime(int createtime) {
            this.createtime = createtime;
        }

        String description;
        String thumb;
        int createtime;
    }
}
