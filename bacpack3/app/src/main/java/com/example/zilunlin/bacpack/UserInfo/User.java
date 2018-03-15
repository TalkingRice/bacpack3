package com.example.zilunlin.bacpack.UserInfo;

/**
 * Created by Zilun Lin on 3/15/2018.
 */

public class User {
    String id, name, type, permission;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public User(String id, String name, String type, String permission) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.permission = permission;


    }

    public User getUser(){
        return User.this;
    }

    public User() {
    }


}
