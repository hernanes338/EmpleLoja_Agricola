package com.aureliohernandez.EmpleLoja_Agricola.Model;

import java.sql.Date;

public class Job {
    String job_name, job_description;
    int job_id;
    User user_id;
    Date start_dt, end_dt;

    public Job(String job_name, String job_description, int job_id, User user_id, Date start_dt, Date end_dt) {
        this.job_name = job_name;
        this.job_description = job_description;
        this.job_id = job_id;
        this.user_id = user_id;
        this.start_dt = start_dt;
        this.end_dt = end_dt;
    }

    public String getJob_name() {
        return job_name;
    }

    public void setJob_name(String job_name) {
        this.job_name = job_name;
    }

    public String getJob_description() {
        return job_description;
    }

    public void setJob_description(String job_description) {
        this.job_description = job_description;
    }

    public int getJob_id() {
        return job_id;
    }

    public void setJob_id(int job_id) {
        this.job_id = job_id;
    }

    public User getUser_id() {
        return user_id;
    }

    public void setUser_id(User user_id) {
        this.user_id = user_id;
    }

    public Date getStart_dt() {
        return start_dt;
    }

    public void setStart_dt(Date start_dt) {
        this.start_dt = start_dt;
    }

    public Date getEnd_dt() {
        return end_dt;
    }

    public void setEnd_dt(Date end_dt) {
        this.end_dt = end_dt;
    }
}
