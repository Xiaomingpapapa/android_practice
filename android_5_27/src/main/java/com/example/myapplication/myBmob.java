package com.example.myapplication;


import cn.bmob.v3.BmobObject;

/**
 * Created by 傻明也有春天 on 2016/8/1.
 */
public class myBmob extends BmobObject{
    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
