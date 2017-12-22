package com.postmark.java;

public class PostmarkErrorCode {

    public int ID;

    public String name;

    public String description;


    public PostmarkErrorCode(String name, String description) {
        this.name=name;
        this.description=description;
    }
}