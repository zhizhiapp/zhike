package com.example.administrator.entity;

import java.io.Serializable;

public class CollectionEntity implements Serializable {
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

    public CollectionEntity.list getList() {
        return list;
    }

    public void setList(CollectionEntity.list list) {
        this.list = list;
    }

    list list;
    public class list {
        int collstatus;

        public int getCollstatus() {
            return collstatus;
        }

        public void setCollstatus(int collstatus) {
            this.collstatus = collstatus;
        }
    }
}
