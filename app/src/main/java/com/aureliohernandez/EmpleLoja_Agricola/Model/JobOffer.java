package com.aureliohernandez.EmpleLoja_Agricola.Model;

import java.sql.Date;

public class JobOffer {
    int jobOffer_id, user_id;
    double salary_hour;
    String title, description, active;
    Date start_date, end_date;

    public JobOffer() {

    }

    public JobOffer(int jobOffer_id, String title, String description, int user_id, Date start_date, Date end_date, double salary_hour, String active) {
        this.jobOffer_id = jobOffer_id;
        this.title = title;
        this.description = description;
        this.user_id = user_id;
        this.salary_hour = salary_hour;
        this.start_date = start_date;
        this.end_date = end_date;
        this.active = active;
    }

    public JobOffer(String title, String description, int user_id,  double salary_hour, Date start_date, Date end_date) {
        this.title = title;
        this.description = description;
        this.user_id = user_id;
        this.salary_hour = salary_hour;
        this.start_date = start_date;
        this.end_date = end_date;
    }

    public int getOffer_id() {
        return jobOffer_id;
    }

    public void setOffer_id(int offer_id) {
        this.jobOffer_id = offer_id;
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

    public Date getStart_date() {
        return start_date;
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    public Date getEnd_date() {
        return end_date;
    }

    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }

    public double getSalary_hour() {
        return salary_hour;
    }

    public void setSalary_hour(double salary_hour) {
        this.salary_hour = salary_hour;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }
}
