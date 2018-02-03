package com.example.administrator.entity;

import java.io.Serializable;

public class CorrectRateEntity implements Serializable {

    int num;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    String name;
}
