package com.aureliohernandez.EmpleLoja_Agricola.Jobs;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.aureliohernandez.EmpleLoja_Agricola.MainActivity;
import com.aureliohernandez.EmpleLoja_Agricola.Model.JobDemand;
import com.aureliohernandez.EmpleLoja_Agricola.Model.JobOffer;
import com.aureliohernandez.EmpleLoja_Agricola.Model.User;
import com.aureliohernandez.EmpleLoja_Agricola.R;
import com.aureliohernandez.EmpleLoja_Agricola.Users.UserLocalStore;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class MyJobs extends AppCompatActivity implements RecyclerViewJobOfferInterface, RecyclerViewJobDemandInterface {

    private User user;
    private JobOffer jobOffer;
    private JobDemand jobDemand;
    private UserLocalStore userLocalStore;
    private ArrayList<JobOffer> jobOffers = new ArrayList<>();
    private ArrayList<JobDemand> jobDemands = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_jobs_screen);

        userLocalStore = new UserLocalStore(this);
        user = userLocalStore.getLoggedInUser();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Mis Trabajos");

        if (user.getRole_id() == 2) {
            fetchMyJobOffers();
        } else if (user.getRole_id() == 3) {
            fetchMyJobDemands();
        }
        else {
            Toast.makeText(getApplicationContext(), "Tipo de usuario incorrecto", Toast.LENGTH_SHORT).show();
        }

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if(user.getRole_id() == 2) {
            RecyclerView recyclerView = findViewById(R.id.recyclerView);

            RecyclerViewJobOffer adapter = new RecyclerViewJobOffer(this, jobOffers, this);

            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));

        } else if (user.getRole_id() == 3) {
            RecyclerView recyclerView = findViewById(R.id.recyclerView);

            RecyclerViewJobDemand adapter = new RecyclerViewJobDemand(this, jobDemands, this);

            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));

        } else {
            Toast.makeText(getApplicationContext(), "Tipo de usuario incorrecto", Toast.LENGTH_SHORT).show();
        }
    }

    // this event will enable the back
    // function to the button on press
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                toMainScreen();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void fetchMyJobOffers() {
        String URL_FETCH_MY_JOB_OFFERS = "http://192.168.0.25/EmpleLoja_Agricola/php_scripts/fetch_my_job_offers.php";

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_FETCH_MY_JOB_OFFERS, new Response.Listener<String>() {
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
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data = new HashMap<>();
                data.put("user_id", String.valueOf(user.getUser_id()));
                return data;
            }
        };

        requestQueue.add(stringRequest);

    }

    public void fetchMyJobDemands() {
        String URL_FETCH_MY_JOB_DEMANDS = "http://192.168.0.25/EmpleLoja_Agricola/php_scripts/fetch_my_job_demands.php";

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_FETCH_MY_JOB_DEMANDS, new Response.Listener<String>() {
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
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data = new HashMap<>();
                data.put("user_id", String.valueOf(user.getUser_id()));
                return data;
            }
        };

        requestQueue.add(stringRequest);

    }

    public void toMainScreen() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
    @Override
    public void onItemClick(int position) {

        if (user.getRole_id() == 2) {
            Intent intent = new Intent (MyJobs.this, JobOfferEdit.class);
            intent.putExtra("Job_Offer_Id", String.valueOf(jobOffers.get(position).getOffer_id()));
            intent.putExtra("Title", jobOffers.get(position).getTitle());
            intent.putExtra("Description", jobOffers.get(position).getDescription());
            intent.putExtra("Start_Date", String.valueOf(jobOffers.get(position).getStart_date()));
            intent.putExtra("End_Date", String.valueOf(jobOffers.get(position).getEnd_date()));
            intent.putExtra("Salary_Hour", String.valueOf(jobOffers.get(position).getSalary_hour()));
            intent.putExtra("User_id", String.valueOf(jobOffers.get(position).getUser_id()));
            intent.putExtra("Active", String.valueOf(jobOffers.get(position).getActive()));
            startActivity(intent);

        } else if (user.getRole_id() == 3) {
            Intent intent = new Intent (MyJobs.this, JobDemandEdit.class);
            intent.putExtra("Job_Demand_Id", String.valueOf(jobDemands.get(position).getDemand_id()));
            intent.putExtra("Title", jobDemands.get(position).getTitle());
            intent.putExtra("Description", jobDemands.get(position).getDescription());
            intent.putExtra("Available_From", String.valueOf(jobDemands.get(position).getAvailable_from()));
            intent.putExtra("User_id", String.valueOf(jobDemands.get(position).getUser_id()));
            intent.putExtra("Active", String.valueOf(jobDemands.get(position).getActive()));

            startActivity(intent);

        } else {
            Toast.makeText(getApplicationContext(), "Tipo de usuario incorrecto", Toast.LENGTH_SHORT).show();
        }

    }
}
