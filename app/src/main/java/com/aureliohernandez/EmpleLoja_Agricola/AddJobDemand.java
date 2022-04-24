package com.aureliohernandez.EmpleLoja_Agricola;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.aureliohernandez.EmpleLoja_Agricola.Model.User;

import java.util.HashMap;
import java.util.Map;

public class AddJobDemand extends AppCompatActivity {

    private EditText nameField, surnameField, phoneField, emailField, passwordField, repasswordField;
    private String name, surname, email, password, repassword;
    private int phone, role_id;
    private RadioGroup roleField;
    private Button signUpButton;
    private User user;

    private Button addJobButton;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_job_demand_screen);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Introduzca los detalles del trabajo");

        addJobButton = (Button) findViewById(R.id.addJobButton);

        addJobButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Se ha creado un nuevo trabajo", Toast.LENGTH_SHORT).show();
                toMainScreen();
            }
        });
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

    public void logIn() {
        final String URL_LOGIN = "http://192.168.0.25/EmpleLoja_Agricola/php_scripts/add_job_demands.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_LOGIN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Res: ", response);
                if (response.equals("success")) {
                    toMainScreen();
                } else if (response.equals("failure")) {
                    Toast.makeText(getApplicationContext(), "Trabajo no creado", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(AddJobDemand.this, error.toString().trim(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data = new HashMap<>();
                data.put("title", user.getEmail());
                data.put("description", user.getPassword());
                data.put("user_id", user.getEmail());
                data.put("available_from", user.getPassword());
                return data;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    public void toMainScreen() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
