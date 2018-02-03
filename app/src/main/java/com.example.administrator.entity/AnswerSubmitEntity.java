package com.example.administrator.entity;

import java.io.Serializable;

/**
 * 管理类联考做完提交后返回的数据实体类
 */
public class AnswerSubmitEntity implements Serializable {
    private String code;
    private String msg;
    private list list;

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

    public AnswerSubmitEntity.list getList() {
        return list;
    }

    public void setList(AnswerSubmitEntity.list list) {
        this.list = list;
    }

    public class list {
        private String paperid;
        private String user_recordid;

        public String getPaperid() {
            return paperid;
        }

        public void setPaperid(String paperid) {
            this.paperid = paperid;
        }

        public String getUser_recordid() {
            return user_recordid;
        }

        public void setUser_recordid(String user_recordid) {
            this.user_recordid = user_recordid;
        }
    }
}
