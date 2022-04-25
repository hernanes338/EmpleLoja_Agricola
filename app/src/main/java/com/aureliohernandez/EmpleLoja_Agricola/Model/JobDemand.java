package com.aureliohernandez.EmpleLoja_Agricola.Model;

import java.sql.Date;

public class JobDemand {
    int demand_id, user_id;
    String title, description, active;
    Date available_from;

    public JobDemand(int demand_id, int user_id, String title, String description, String active, Date available_from) {
        this.demand_id = demand_id;
        this.user_id = user_id;
        this.title = title;
        this.description = description;
        this.active = active;
        this.available_from = available_from;
    }

    public JobDemand(String title, String description, int user_id, Date available_from) {
        this.title = title;
        this.description = description;
        this.user_id = user_id;
        this.available_from = available_from;
    }



    public int getDemand_id() {
        return demand_id;
    }

    public void setDemand_id(int demand_id) {
        this.demand_id = demand_id;
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

    public void setAvailable_from(Date available_from) {
        this.available_from = available_from;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

}
