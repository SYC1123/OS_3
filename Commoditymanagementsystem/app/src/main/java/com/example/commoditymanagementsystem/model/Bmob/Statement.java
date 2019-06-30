package com.example.commoditymanagementsystem.model.Bmob;

import cn.bmob.v3.BmobObject;

public class Statement extends BmobObject {
    String name;
    int num;
    float price;
    String state;//0是卖，1是买

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getNum() {
        return num;
    }

    public float getPrice() {
        return price;
    }

    public String getState() {
        return state;
    }
}

