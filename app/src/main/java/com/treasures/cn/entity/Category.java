package com.treasures.cn.entity;

import java.io.Serializable;
import java.util.ArrayList;

public class Category implements Serializable {
    private int id = 0;
    private String name = "";
    private ArrayList<CategoryType> typeArr = new ArrayList<>();

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

    public ArrayList<CategoryType> getTypeArr() {
        return typeArr;
    }

    public void setTypeArr(ArrayList<CategoryType> typeArr) {
        this.typeArr = typeArr;
    }
}
