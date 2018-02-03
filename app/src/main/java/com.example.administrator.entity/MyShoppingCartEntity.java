package com.example.administrator.entity;

import java.io.Serializable;

public class MyShoppingCartEntity implements Serializable {
    String title = "";
    double money = 0;
    boolean flag = true;

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public boolean isChoosed() {

        return isChoosed;
    }

    public void setChoosed(boolean choosed) {
        isChoosed = choosed;
    }

    boolean isChoosed = false;


    public MyShoppingCartEntity() {
        super();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }


}
