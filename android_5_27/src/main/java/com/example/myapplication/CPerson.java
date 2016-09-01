package com.example.myapplication;

import java.io.Serializable;

/**
 * Created by 傻明也有春天 on 2016/5/27.
 */
public class CPerson implements Serializable {
    private String name;
    private String sex;
    private String age;
    private String path;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
