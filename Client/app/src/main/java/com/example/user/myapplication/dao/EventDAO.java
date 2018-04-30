package com.example.user.myapplication.dao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;

@Entity()
public class EventDAO {
    @Id
    private String id;

    @NotNull
    private String title;
    @NotNull
    private String description;
    @NotNull
    private String phone;

    @Generated(hash = 1184884408)
    public EventDAO(String id, @NotNull String title, @NotNull String description, @NotNull String phone) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.phone = phone;

    }

    @Generated(hash = 1645973274)
    public EventDAO() {
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String name) {
        this.title = title;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription( String description) {
        this.description = description;
    }
    public String getPhone() {
        return this.phone;
    }

    public void setPhone( String phone) {
        this.phone = phone;
    }


}
