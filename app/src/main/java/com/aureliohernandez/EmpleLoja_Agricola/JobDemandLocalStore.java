package com.aureliohernandez.EmpleLoja_Agricola;

import android.content.Context;
import android.content.SharedPreferences;

import com.aureliohernandez.EmpleLoja_Agricola.Model.JobDemand;
import com.aureliohernandez.EmpleLoja_Agricola.Model.JobOffer;

import java.sql.Date;

public class JobDemandLocalStore {

    public static final String SP_NAME = "jobDemandDetails";
    SharedPreferences jobDemandLocalDatabase;

    public JobDemandLocalStore(Context context) {
        jobDemandLocalDatabase = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
    }

    public void storeJobDemandData(JobOffer jobOffer) {
        SharedPreferences.Editor sharedPreferencesEditor = jobDemandLocalDatabase.edit();
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

    public JobDemand getJobDemandData() {
        int job_id = jobDemandLocalDatabase.getInt("job_id", 0);
        String title = jobDemandLocalDatabase.getString("title", "");
        String description = jobDemandLocalDatabase.getString("description", "");
        int user_id = jobDemandLocalDatabase.getInt("user_id", 0);
        Date available_from = Date.valueOf(jobDemandLocalDatabase.getString("available_from", ""));
        String active = jobDemandLocalDatabase.getString("active", "");

        JobDemand jobDemand = new JobDemand(job_id, title, description, user_id, available_from, active);

        return jobDemand;
    }

    public void clearUserData(){
        SharedPreferences.Editor sharedPreferencesEditor = jobDemandLocalDatabase.edit();
        sharedPreferencesEditor.clear();
        sharedPreferencesEditor.commit();
    }
}
