package com.kate.shop.entity;

//TODO permissions table
public class Permission {
    private Short id;
    private String name;

    public Short getId() {
        return id;
    }

    public Permission setId(Short id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Permission setName(String name) {
        this.name = name;
        return this;
    }
}
