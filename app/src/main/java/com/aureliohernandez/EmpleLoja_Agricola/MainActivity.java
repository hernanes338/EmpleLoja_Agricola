package com.aureliohernandez.EmpleLoja_Agricola;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
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
import com.aureliohernandez.EmpleLoja_Agricola.Model.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private UserLocalStore userLocalStore;
    // Casa
    private final String URL = "http://192.168.0.25/EmpleLoja_Agricola/php_scripts/fetch_job_offers.php";
    //Oficina
    //private final String URL = "http://192.168.1.130/EmpleLoja_Agricola/php_scripts/fetch_job_offers.php";

    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Bienvenido, ");

        BottomNavigationView bottomNavigationView = (BottomNavigationView)findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.refresh:
                        // Toast.makeText(getApplicationContext(), "Se ha actualizado la lista de trabajos", Toast.LENGTH_SHORT).show();
                        displayData();
                        break;
                    case R.id.addJob:
                        toAddJobActivity();
                        break;
                    case R.id.myJobs:
                        toMyJobsActivity();
                        break;
                }
                return false;
            }
        });

        userLocalStore = new UserLocalStore(this);

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
                // userLocalStore.setUserLoggedIn(false);
                toLogInActivity();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void displayData() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                response -> Toast.makeText(MainActivity.this, response.toString(), Toast.LENGTH_LONG).show(),
                error -> Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_LONG).show()) {
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    public void jsonParse() {
        String URL_JSON = "";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URL_JSON, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("user");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        int id = jsonObject.getInt("id");
                        String name = jsonObject.getString("name");
                        String surname = jsonObject.getString("surname");
                        String email = jsonObject.getString("email");
                        int phone = jsonObject.getInt("phone");
                        String  password = jsonObject.getString("password");
                        int role = jsonObject.getInt("role");

                        user = new User(id, name, surname, phone, email, password, role);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(jsonObjectRequest);

    }


    public void toLogInActivity(){
        Intent intent = new Intent(this, LogIn.class);
        startActivity(intent);
        finish();
    }

    public void toAddJobActivity(){
        Intent intent = new Intent(this, AddJob.class);
        startActivity(intent);
        finish();
    }

    public void toMyJobsActivity(){
        Intent intent = new Intent(this, MyJobs.class);
        startActivity(intent);
        finish();
    }
}