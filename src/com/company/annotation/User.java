package com.company.annotation;

@SetObjectFields(name = "Just User",
        instantiated = false)
public class User {

    private String name;
    private int num;

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", num=" + num +
                '}';
    }
}
