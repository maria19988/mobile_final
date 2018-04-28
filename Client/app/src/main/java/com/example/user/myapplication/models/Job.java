package com.example.user.myapplication.models;

import com.google.gson.annotations.SerializedName;

public class Job
{
    @SerializedName("_id")
    private String id;
    private String title;
    private String description;
    private String phone;

    public Job(String title, String description, String phone) {
        this.title = title;
        this.description = description;
        this.phone = phone;
    }

    public Job(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
