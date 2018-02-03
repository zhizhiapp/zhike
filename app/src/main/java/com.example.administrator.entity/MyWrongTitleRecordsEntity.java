package com.example.administrator.entity;

import java.io.Serializable;
import java.util.ArrayList;

public class MyWrongTitleRecordsEntity implements Serializable {


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
    public MyWrongTitleRecordsEntity.list getList() {
        return list;
    }
    public void setList(MyWrongTitleRecordsEntity.list list) {
        this.list = list;
    }
    list list;
    public class list {
        int all_error_number;
        String error_max_name;
        ArrayList<chapterList> chapter_list;

        public int getAll_error_number() {
            return all_error_number;
        }
        public void setAll_error_number(int all_error_number) {
            this.all_error_number = all_error_number;
        }
        public String getError_max_name() {
            return error_max_name;
        }
        public void setError_max_name(String error_max_name) {
            this.error_max_name = error_max_name;
        }
        public ArrayList<chapterList> getChapter_list() {
            return chapter_list;
        }
        public void setChapter_list(ArrayList<chapterList> chapter_list) {
            this.chapter_list = chapter_list;
        }

        public class chapterList {
            int id;
            String name="";
            int error_num;
            public int getId() {
                return id;
            }
            public void setId(int id) {
                this.id = id;
            }
            public String getName() {
                return name;
            }
            public void setName(String name) {
                this.name = name;
            }
            public int getError_num() {
                return error_num;
            }
            public void setError_num(int error_num) {
                this.error_num = error_num;
            }



        }

    }


}
