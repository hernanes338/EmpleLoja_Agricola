package com.aureliohernandez.EmpleLoja_Agricola.Jobs;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
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
import com.aureliohernandez.EmpleLoja_Agricola.URLManagement;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class EditJobDemand extends AppCompatActivity {
    private TextView titleTextView, descriptionTextView, availableFromTextView, endDateTextView, salaryTextView;
    private String title, description, availableFrom, active;
    private Button updateButton;
    JobDemand jobDemand;
    private RadioGroup activeField;

    Calendar calendar;
    EditText clickedEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_job_demand_screen);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Editar la demanda");

        titleTextView = findViewById(R.id.editDemandTitle);
        descriptionTextView = findViewById(R.id.editDemandDescription);
        availableFromTextView = findViewById(R.id.editDemandAvailableFrom);

        calendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                updateCalendar();
            }

            private void updateCalendar() {
                String Format = "yyyy-MM-dd";
                SimpleDateFormat sdf = new SimpleDateFormat(Format, Locale.ITALY);
                clickedEditText.setText(sdf.format(calendar.getTime()));
            }
        };

        availableFromTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickedEditText = (EditText) v;
                new DatePickerDialog(EditJobDemand.this, date, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        title = getIntent().getStringExtra("Title");
        description = getIntent().getStringExtra("Description");
        availableFrom = getIntent().getStringExtra("Available_From");

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

    // El evento de item seleccionado (barra hacia detras) permite volver a la pantalla de login
    // al  pulsar el boton home (flecha hacia detras)
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

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLManagement.URL_EDIT_JOB_DEMAND, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equals("success")) {
                    Toast.makeText(getApplicationContext(), "Se ha editado la demanda de trabajo", Toast.LENGTH_SHORT).show();
                    toMyJobs();
                } else if (response.equals("failure")) {
                    Toast.makeText(getApplicationContext(), "La oferta de demanda no se ha editado", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(EditJobDemand.this, error.toString().trim(), Toast.LENGTH_SHORT).show();
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
