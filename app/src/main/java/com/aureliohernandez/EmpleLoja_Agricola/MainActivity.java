package com.aureliohernandez.EmpleLoja_Agricola;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.aureliohernandez.EmpleLoja_Agricola.Model.JobDemand;
import com.aureliohernandez.EmpleLoja_Agricola.Model.JobOffer;
import com.aureliohernandez.EmpleLoja_Agricola.Model.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private User user;
    private JobOffer jobOffer;
    private JobDemand jobDemand;
    private UserLocalStore userLocalStore;
    private ArrayList<JobOffer> jobOffers = new ArrayList<JobOffer>();
    private ArrayList<JobDemand> jobDemands = new ArrayList<JobDemand>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userLocalStore = new UserLocalStore(this);
        user = userLocalStore.getLoggedInUser();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("¡Bienvenido " + user.getName() + "!");

        BottomNavigationView bottomNavigationView = (BottomNavigationView)findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.refresh:
                        if (user.getRole_id() == 2) {
                            fetchJobDemands();
                        } else if (user.getRole_id() == 3) {
                            fetchJobOffers();
                        } else {
                            Toast.makeText(getApplicationContext(), "Tipo de usuario incorrecto", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case R.id.addJob:
                        if (user.getRole_id() == 2) {
                            toAddJobOfferActivity();
                        } else if (user.getRole_id() == 3) {
                            toAddJobDemandActivity();
                        } else {
                            Toast.makeText(getApplicationContext(), "Tipo de usuario incorrecto", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case R.id.myJobs:
                        toMyJobsActivity();
                        break;
                }
                return false;
            }
        });

        if(user.getRole_id() == 2) {
            fetchJobDemands();
        } else if (user.getRole_id() == 3) {
            fetchJobOffers();
        } else {
            Toast.makeText(getApplicationContext(), "Tipo de usuario incorrecto", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logOut:
                userLocalStore.clearUserData();
                toLogInActivity();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void fetchJobOffers() {
        String URL_FETCH_JOB_OFFERS = "http://192.168.0.25/EmpleLoja_Agricola/php_scripts/fetch_job_offers.php";

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_FETCH_JOB_OFFERS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);

                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        int id = jsonObject.getInt("ID");
                        String title = jsonObject.getString("TITLE");
                        String description = jsonObject.getString("DESCRIPTION");
                        int user_id = jsonObject.getInt("USER_ID");
                        Date start_date = Date.valueOf(jsonObject.getString("START_DATE"));
                        Date end_date = Date.valueOf(jsonObject.getString("END_DATE"));
                        double salary_hour = Double.parseDouble(jsonObject.getString("SALARY_HOUR"));
                        String active = jsonObject.getString("ACTIVE");

                        jobOffer = new JobOffer(id, title, description, user_id, start_date, end_date, salary_hour, active);

                        jobOffers.add(jobOffer);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    for(int i = 0; i < jobOffers.size(); i++)
                    {
                        JobOffer jobOffer = jobOffers.get(i);
                        System.out.println("Usuario ofertante: " + jobOffer.getOffer_id() + "\t" + jobOffer.getTitle() + "\t" + jobOffer.getDescription()
                                + "\t" + jobOffer.getUser_id() + "\t" + jobOffer.getStart_date() + "\t" + jobOffer.getEnd_date()
                                + "\t" + jobOffer.getSalary_hour() + "\t" + jobOffer.getActive());
                    }
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("Response failed: " + error);
            }
        });

        requestQueue.add(stringRequest);

    }

    public void fetchJobDemands() {
        String URL_FETCH_JOB_DEMANDS = "http://192.168.0.25/EmpleLoja_Agricola/php_scripts/fetch_job_demands.php";

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_FETCH_JOB_DEMANDS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);

                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        int id = jsonObject.getInt("ID");
                        String title = jsonObject.getString("TITLE");
                        String description = jsonObject.getString("DESCRIPTION");
                        int user_id = jsonObject.getInt("USER_ID");
                        Date available_from = Date.valueOf(jsonObject.getString("AVAILABLE_FROM"));
                        String active = jsonObject.getString("ACTIVE");

                        jobDemand = new JobDemand(id, title, description, user_id, available_from, active);

                        jobDemands.add(jobDemand);

                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    for(int i = 0; i < jobDemands.size(); i++)
                    {
                        JobDemand jobDemand = jobDemands.get(i);
                        System.out.println("Usuario demandante: " + jobDemand.getDemand_id() + "\t" + jobDemand.getTitle() + "\t" + jobDemand.getDescription()+ "\t"
                                + jobDemand.getUser_id() + "\t" + jobDemand.getAvailable_from() + "\t" + jobDemand.getActive());
                    }
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("Response failed: " + error);
            }
        });

        requestQueue.add(stringRequest);

    }

    public void toLogInActivity(){
        Intent intent = new Intent(this, LogIn.class);
        startActivity(intent);
        finish();
    }

    public void toAddJobOfferActivity(){
        Intent intent = new Intent(this, AddJobOffer.class);
        startActivity(intent);
        finish();
    }

    public void toAddJobDemandActivity(){
        Intent intent = new Intent(this, AddJobDemand.class);
        startActivity(intent);
        finish();
    }

    public void toMyJobsActivity(){
        Intent intent = new Intent(this, MyJobs.class);
        startActivity(intent);
        finish();
    }
}