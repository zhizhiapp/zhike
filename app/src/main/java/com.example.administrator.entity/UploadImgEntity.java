package com.example.administrator.entity;

import java.io.Serializable;

public class UploadImgEntity implements Serializable {

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

    public UploadImgEntity.list getList() {
        return list;
    }

    public void setList(UploadImgEntity.list list) {
        this.list = list;
    }

    list list;

    public class list {
        String headimgurl;

        public String getHeadimgurl() {
            return headimgurl;
        }

        public void setHeadimgurl(String headimgurl) {
            this.headimgurl = headimgurl;
        }
    }
}
