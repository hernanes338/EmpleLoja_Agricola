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
import com.aureliohernandez.EmpleLoja_Agricola.Model.JobDemand;
import com.aureliohernandez.EmpleLoja_Agricola.Model.JobOffer;
import com.aureliohernandez.EmpleLoja_Agricola.Model.User;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

public class AddJobDemand extends AppCompatActivity {

    private EditText titleField, descriptionField, availableFromField;
    private String title, description;
    private int user_id;
    private Date availableFrom;
    private Button addJobDemandButton;
    private JobDemand jobDemand;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_job_demand_screen);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Detalles de la demanda");

        titleField = (EditText) findViewById(R.id.title);
        descriptionField = (EditText) findViewById(R.id.description);
        availableFromField = (EditText) findViewById(R.id.availableFromDate);

        addJobDemandButton = (Button) findViewById(R.id.addJobDemandButton);

        addJobDemandButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title = titleField.getText().toString().trim();
                description = descriptionField.getText().toString().trim();
                user_id = 3; // TO DO: obtener el user_id del archivo sharedPreferences
                availableFrom = Date.valueOf(availableFromField.getText().toString().trim());

                jobDemand = new JobDemand(title, description, user_id, availableFrom);
                addJobDemand();
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

    public void addJobDemand() {
        final String URL_LOGIN = "http://192.168.0.25/EmpleLoja_Agricola/php_scripts/add_job_demands.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_LOGIN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Res: ", response);
                if (response.equals("success")) {
                    Toast.makeText(getApplicationContext(), "Se ha creado una nueva demanda de trabajo", Toast.LENGTH_SHORT).show();
                    toMainScreen();
                } else if (response.equals("failure")) {
                    Toast.makeText(getApplicationContext(), "Demanda de trabajo no creada", Toast.LENGTH_SHORT).show();
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
                data.put("title", jobDemand.getTitle());
                data.put("description", jobDemand.getDescription());
                data.put("user_id", Integer.toString(jobDemand.getUser_id()));
                data.put("available_from", jobDemand.getAvailable_from().toString());
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
