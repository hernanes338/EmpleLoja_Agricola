package com.aureliohernandez.EmpleLoja_Agricola.Model;

import java.sql.Date;

public class JobDemand {
    private int jobDemand_id, user_id, phone;
    private String title, description, active;
    private Date available_from;

    public JobDemand(int jobDemand_id, String title, String description, int user_id, Date available_from, String active) {
        this.jobDemand_id = jobDemand_id;
        this.user_id = user_id;
        this.title = title;
        this.description = description;
        this.active = active;
        this.available_from = available_from;
    }

    public JobDemand(int jobDemand_id, String title, String description, int user_id, Date available_from, String active, int phone) {
        this.jobDemand_id = jobDemand_id;
        this.user_id = user_id;
        this.title = title;
        this.description = description;
        this.active = active;
        this.available_from = available_from;
        this.phone = phone;
    }

    public JobDemand(String title, String description, int user_id, Date available_from) {
        this.title = title;
        this.description = description;
        this.user_id = user_id;
        this.available_from = available_from;
    }

    public int getDemand_id() {
        return jobDemand_id;
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

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public Date getAvailable_from() {
        return available_from;
    }

    public String getActive() {
        return active;
    }

    public int getPhone() {
        return phone;
    }

}
