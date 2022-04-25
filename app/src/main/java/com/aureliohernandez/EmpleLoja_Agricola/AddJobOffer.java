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
import com.aureliohernandez.EmpleLoja_Agricola.Model.JobOffer;
import com.aureliohernandez.EmpleLoja_Agricola.Model.User;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

public class AddJobOffer extends AppCompatActivity {

    private EditText titleField, descriptionField, salaryHourField, startDateField, endDateField;
    private String title, description;
    private int user_id;
    private double salaryHour;
    private Date startDate, endDate;
    private Button addJobOfferButton;
    private JobOffer jobOffer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_job_offer_screen);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Detalles de la oferta");

        titleField = (EditText) findViewById(R.id.title);
        descriptionField = (EditText) findViewById(R.id.description);
        salaryHourField = (EditText) findViewById(R.id.salaryHour);
        startDateField = (EditText) findViewById(R.id.startDate);
        endDateField = (EditText) findViewById(R.id.endDate);

        addJobOfferButton = (Button) findViewById(R.id.addJobOfferButton);

        addJobOfferButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                title = titleField.getText().toString().trim();
                description = descriptionField.getText().toString().trim();
                user_id = 2; // TO DO: obtener el user_id del archivo sharedPreferences
                salaryHour = Double.parseDouble(salaryHourField.getText().toString().trim());
                startDate = Date.valueOf(startDateField.getText().toString().trim());
                endDate = Date.valueOf(endDateField.getText().toString().trim());

                jobOffer = new JobOffer(title, description, user_id, salaryHour, startDate, endDate);
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
        final String URL_LOGIN = "http://192.168.0.25/EmpleLoja_Agricola/php_scripts/add_job_offers.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_LOGIN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Res: ", response);
                if (response.equals("success")) {
                    Toast.makeText(getApplicationContext(), "Se ha creado una nueva oferta de trabajo", Toast.LENGTH_SHORT).show();
                    toMainScreen();
                } else if (response.equals("failure")) {
                    Toast.makeText(getApplicationContext(), "Oferta de trabajo no creado", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(AddJobOffer.this, error.toString().trim(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data = new HashMap<>();
                data.put("title", jobOffer.getTitle());
                data.put("description", jobOffer.getDescription());
                data.put("user_id", Integer.toString(jobOffer.getUser_id()));
                data.put("start_date", jobOffer.getStart_date().toString());
                data.put("end_date", jobOffer.getEnd_date().toString());
                data.put("salary_hour", Double.toString(jobOffer.getSalary_hour()));

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