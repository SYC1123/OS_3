package com.example.commoditymanagementsystem.model.Bmob;

import cn.bmob.v3.BmobUser;

public class User extends BmobUser {
    int flag;//1，管理员 2，老板 3，采购部门 4，销售部门

    public void setFlag(int flag) {
        this.flag = flag;
    }
}
