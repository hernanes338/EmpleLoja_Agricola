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

public class MainActivity extends AppCompatActivity {

    // Casa
    private final String URL_FETCH_JOB_OFFERS = "http://192.168.0.25/EmpleLoja_Agricola/php_scripts/fetch_job_offers.php";
    private final String URL_FETCH_JOB_DEMANDS = "http://192.168.0.25/EmpleLoja_Agricola/php_scripts/fetch_job_demands.php";
    //Oficina

    private User user;
    private JobOffer jobOffer;
    private JobDemand jobDemand;
    private UserLocalStore userLocalStore;


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
                            fetchJobOffers();
                        } else if (user.getRole_id() == 3) {
                            fetchJobDemands();
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
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_FETCH_JOB_OFFERS,
                response -> Toast.makeText(MainActivity.this, response.toString(), Toast.LENGTH_LONG).show(),
                error -> Toast.makeText(MainActivity.this, "Error: " +error, Toast.LENGTH_LONG).show()) {
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    public void fetchJobDemands() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_FETCH_JOB_DEMANDS,
                response -> Toast.makeText(MainActivity.this, response.toString(), Toast.LENGTH_LONG).show(),
                error -> Toast.makeText(MainActivity.this, "Error: " +error, Toast.LENGTH_LONG).show()) {
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
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