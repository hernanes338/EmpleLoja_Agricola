package com.aureliohernandez.EmpleLoja_Agricola;

import android.content.Context;
import android.content.SharedPreferences;

import com.aureliohernandez.EmpleLoja_Agricola.Model.JobOffer;
import com.aureliohernandez.EmpleLoja_Agricola.Model.User;

import java.sql.Date;

public class JobOfferLocalStore {

    public static final String SP_NAME = "jobOfferDetails";
    SharedPreferences jobOfferLocalDatabase;

    public JobOfferLocalStore(Context context) {
        jobOfferLocalDatabase = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
    }

    public void storeJobOfferData(JobOffer jobOffer) {
        SharedPreferences.Editor sharedPreferencesEditor = jobOfferLocalDatabase.edit();
        sharedPreferencesEditor.putInt("job_id", jobOffer.getUser_id());
        sharedPreferencesEditor.putString("title", jobOffer.getTitle());
        sharedPreferencesEditor.putString("description", jobOffer.getDescription());
        sharedPreferencesEditor.putInt("user_id", jobOffer.getUser_id());
        sharedPreferencesEditor.putString("start_date", jobOffer.getStart_date().toString());
        sharedPreferencesEditor.putString("end_date", jobOffer.getEnd_date().toString());
        sharedPreferencesEditor.putString("salary_hour", String.valueOf(jobOffer.getSalary_hour()));
        sharedPreferencesEditor.putString("active", jobOffer.getActive());
        sharedPreferencesEditor.commit();
    }

    public JobOffer getJobOfferData() {
        int job_id = jobOfferLocalDatabase.getInt("job_id", 0);
        String title = jobOfferLocalDatabase.getString("title", "");
        String description = jobOfferLocalDatabase.getString("description", "");
        int user_id = jobOfferLocalDatabase.getInt("user_id", 0);
        Date start_date = Date.valueOf(jobOfferLocalDatabase.getString("start_date", ""));
        Date end_date = Date.valueOf(jobOfferLocalDatabase.getString("end_date", ""));
        double salary_hour = Double.parseDouble(jobOfferLocalDatabase.getString("salary_hour", ""));
        String active = jobOfferLocalDatabase.getString("active", "");

        JobOffer jobOffer = new JobOffer(job_id, title, description, user_id, start_date, end_date, salary_hour, active);

        return jobOffer;
    }

    public void clearUserData(){
        SharedPreferences.Editor sharedPreferencesEditor = jobOfferLocalDatabase.edit();
        sharedPreferencesEditor.clear();
        sharedPreferencesEditor.commit();
    }
}
