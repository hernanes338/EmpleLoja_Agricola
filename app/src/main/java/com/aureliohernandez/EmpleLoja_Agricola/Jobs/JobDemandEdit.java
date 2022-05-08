package com.aureliohernandez.EmpleLoja_Agricola.Jobs;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
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
import com.aureliohernandez.EmpleLoja_Agricola.R;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

public class JobDemandEdit extends AppCompatActivity {
    private TextView titleTextView, descriptionTextView, availableFromTextView, endDateTextView, salaryTextView;
    private String title, description, availableFrom, active;
    private Button updateButton;
    JobDemand jobDemand;
    private RadioGroup activeField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_job_demand_screen);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Editar la demanda");

        title = getIntent().getStringExtra("Title");
        description = getIntent().getStringExtra("Description");
        availableFrom = getIntent().getStringExtra("Available_From");

        titleTextView = findViewById(R.id.editDemandTitle);
        descriptionTextView = findViewById(R.id.editDemandDescription);
        availableFromTextView = findViewById(R.id.editDemandAvailableFrom);

        titleTextView.setText(title);
        descriptionTextView.setText(description);
        availableFromTextView.setText(availableFrom);

        active = "Y";

        activeField = (RadioGroup) findViewById(R.id.jobDemandActive);

        activeField.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.editDemandActiveYes:
                        active = "Y";
                        break;
                    case R.id.editDemandActiveNo:
                        active = "N";
                        break;
                }
            }
        });

        updateButton = (Button) findViewById(R.id.editDemandUpdateButton);

        updateButton.setOnClickListener(new View.OnClickListener() {
            int jobDemand_id, user_id;
            String title, description;
            Date availableFrom;

            @Override
            public void onClick(View v) {

                jobDemand_id = user_id = 0;
                title = description = "";

                availableFrom = null;

                jobDemand_id = Integer.parseInt(getIntent().getStringExtra("Job_Demand_Id"));
                title = titleTextView.getText().toString().trim();
                description = descriptionTextView.getText().toString().trim();
                user_id = Integer.parseInt(getIntent().getStringExtra("User_id"));
                if (availableFromTextView.getText().toString().trim().length() > 0) {
                    availableFrom = Date.valueOf(availableFromTextView.getText().toString());
                }

                activeField = (RadioGroup) findViewById(R.id.jobDemandActive);

                activeField.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
                {
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        switch(checkedId){
                            case R.id.editDemandActiveYes:
                                active = "Y";
                                break;
                            case R.id.editDemandActiveNo:
                                active = "N";
                                break;
                        }
                    }
                });

                if (!title.equals("") && !description.equals("") && !(availableFrom == null)) {

                    jobDemand = new JobDemand(jobDemand_id, title, description, user_id, availableFrom, active);
                    updateJobDemand();

                } else{
                    Toast.makeText(getApplicationContext(), "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    // this event will enable the back
    // function to the button on press
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                toMyJobs();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void updateJobDemand() {
        final String URL_EDIT_JOB_DEMAND = "http://192.168.0.25/EmpleLoja_Agricola/php_scripts/edit_job_demands.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_EDIT_JOB_DEMAND, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equals("success")) {
                    Toast.makeText(getApplicationContext(), "Se ha editado la oferta de trabajo", Toast.LENGTH_SHORT).show();
                    toMyJobs();
                } else if (response.equals("failure")) {
                    Toast.makeText(getApplicationContext(), "La oferta de trabajo no se ha editado", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(JobDemandEdit.this, error.toString().trim(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data = new HashMap<>();
                data.put("job_demand_id", Integer.toString(jobDemand.getDemand_id()));
                data.put("title", jobDemand.getTitle());
                data.put("description", jobDemand.getDescription());
                data.put("available_from", jobDemand.getAvailable_from().toString());
                data.put("active", jobDemand.getActive());
                return data;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    public void toMyJobs() {
        Intent intent = new Intent(this, MyJobs.class);
        startActivity(intent);
        finish();
    }
}
