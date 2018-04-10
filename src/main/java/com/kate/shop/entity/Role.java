package com.kate.shop.entity;

import java.util.Set;

//TODO roles table
public class Role {
    private Short id;
    private String name;
    private Set<Permission> permissions; //TODO many to many relationship

    public Short getId() {
        return id;
    }

    public Role setId(Short id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Role setName(String name) {
        this.name = name;
        return this;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }

    public Role setPermissions(Set<Permission> permissions) {
        this.permissions = permissions;
        return this;
    }
}
