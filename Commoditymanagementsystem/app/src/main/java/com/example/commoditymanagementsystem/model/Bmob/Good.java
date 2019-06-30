package com.example.commoditymanagementsystem.model.Bmob;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;

public class Good extends BmobObject implements Serializable {
    private String name;//商品名称
    private String category;//商品类别
    private int num;//商品数量
    private float unit_price;//单价
    private String location;//位置

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public int getNum() {
        return num;
    }

    public float getUnit_price() {
        return unit_price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public void setUnit_price(float unit_price) {
        this.unit_price = unit_price;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
