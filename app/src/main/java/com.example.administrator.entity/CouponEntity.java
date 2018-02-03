package com.example.administrator.entity;

import java.io.Serializable;
import java.util.ArrayList;

public class CouponEntity implements Serializable{
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public ArrayList<CouponEntity.list> getList() {
        return list;
    }

    public void setList(ArrayList<CouponEntity.list> list) {
        this.list = list;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    String code;
    String msg;
    ArrayList<list>list;
    public class list{
        int id;
        int couponMoney;
        int spendMoney;
        int integral;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getCouponMoney() {
            return couponMoney;
        }

        public void setCouponMoney(int couponMoney) {
            this.couponMoney = couponMoney;
        }

        public int getIntegral() {
            return integral;
        }

        public void setIntegral(int integral) {
            this.integral = integral;
        }

        public int getSpendMoney() {
            return spendMoney;
        }

        public void setSpendMoney(int spendMoney) {
            this.spendMoney = spendMoney;
        }

        public int getCouponType() {
            return couponType;
        }

        public void setCouponType(int couponType) {
            this.couponType = couponType;
        }

        public int getValidStartTime() {
            return validStartTime;
        }

        public void setValidStartTime(int validStartTime) {
            this.validStartTime = validStartTime;
        }

        public int getValidEndTime() {
            return validEndTime;
        }

        public void setValidEndTime(int validEndTime) {
            this.validEndTime = validEndTime;
        }

        int couponType;
        int validStartTime;
        int validEndTime;
    }


}
