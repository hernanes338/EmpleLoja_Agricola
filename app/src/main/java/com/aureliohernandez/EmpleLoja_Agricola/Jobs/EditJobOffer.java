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
import com.aureliohernandez.EmpleLoja_Agricola.Model.JobOffer;
import com.aureliohernandez.EmpleLoja_Agricola.R;
import com.aureliohernandez.EmpleLoja_Agricola.URLManagement;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @class Clase que permite a un usuario con rol ofertante visualizar los detalles de una oferta de trabajo
 * creada por el mismo y editarlos si asi lo desea. Tambien permite la opcion de inactivar permanentemente
 * la oferta en particular
 */

public class EditJobOffer extends AppCompatActivity {
    private TextView titleTextView, descriptionTextView, startDateTextView, endDateTextView, salaryTextView;
    private String title, description, startDate, endDate, salary, active;
    private Button updateButton;
    JobOffer jobOffer;
    private RadioGroup activeField;

    Calendar calendar;
    EditText clickedEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_job_offer_screen);

        Toolbar toolbar = findViewById(R.id.toolbar);
        // Mostrar barra superior
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        // Mostrar el boton de flecha hacia detras
        actionBar.setDisplayHomeAsUpEnabled(true);
        // Mostrar texto en la barra superior
        actionBar.setTitle("Editar la oferta");

        titleTextView = findViewById(R.id.editOfferTitle);
        descriptionTextView = findViewById(R.id.editOfferDescription);
        startDateTextView = findViewById(R.id.editOfferStartDate);
        endDateTextView = findViewById(R.id.editOfferEndDate);
        salaryTextView = findViewById(R.id.editOfferSalary);

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

        startDateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickedEditText = (EditText) v;
                new DatePickerDialog(EditJobOffer.this, date, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        endDateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickedEditText = (EditText) v;
                new DatePickerDialog(EditJobOffer.this, date, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        title = getIntent().getStringExtra("Title");
        description = getIntent().getStringExtra("Description");
        startDate = getIntent().getStringExtra("Start_Date");
        endDate = getIntent().getStringExtra("End_Date");
        salary = getIntent().getStringExtra("Salary_Hour");

        titleTextView.setText(title);
        descriptionTextView.setText(description);
        startDateTextView.setText(startDate);
        endDateTextView.setText(endDate);
        salaryTextView.setText(salary);

        active = "Y";

        activeField = (RadioGroup) findViewById(R.id.jobOfferActive);

        activeField.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.editOfferActiveYes:
                        active = "Y";
                        break;
                    case R.id.editOfferActiveNo:
                        active = "N";
                        break;
                }
            }
        });

        updateButton = (Button) findViewById(R.id.editOfferUpdateButton);

        updateButton.setOnClickListener(new View.OnClickListener() {
            int jobOffer_id, user_id;
            String title, description;
            Date startDate, endDate;
            double salary;

            @Override
            public void onClick(View v) {

                jobOffer_id = user_id = 0;
                title = description = "";
                startDate = endDate = null;
                salary = 0;


                jobOffer_id = Integer.parseInt(getIntent().getStringExtra("Job_Offer_Id"));
                title = titleTextView.getText().toString().trim();
                description = descriptionTextView.getText().toString().trim();
                user_id = Integer.parseInt(getIntent().getStringExtra("User_id"));
                if (startDateTextView.getText().toString().trim().length() > 0) {
                    startDate = Date.valueOf(startDateTextView.getText().toString());
                }
                if (endDateTextView.getText().toString().trim().length() > 0) {
                    endDate = Date.valueOf(endDateTextView.getText().toString());
                }
                if (salaryTextView.getText().toString().trim().length() > 0) {
                    salary = Double.parseDouble(salaryTextView.getText().toString());
                }

                if (!title.equals("") && !description.equals("") && !(startDate == null) && !(endDate == null) && salary != 0) {

                    jobOffer = new JobOffer(jobOffer_id, title, description, user_id, startDate, endDate, salary, active);
                    updateJobOffer();

                } else {
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

    /**
     * Metodo que permite editar los detalles de una oferta de trabajo en la base de datos.
     */

    public void updateJobOffer() {
        // Peticion de un String desde la URL
        final StringRequest stringRequest = new StringRequest(Request.Method.POST, URLManagement.URL_EDIT_JOB_OFFER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) { // En caso de respuesta valida se evalua el String devuelto
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
                Toast.makeText(EditJobOffer.this, error.toString().trim(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data = new HashMap<>();
                data.put("job_offer_id", Integer.toString(jobOffer.getOffer_id()));
                data.put("title", jobOffer.getTitle());
                data.put("description", jobOffer.getDescription());
                data.put("start_date", jobOffer.getStart_date().toString());
                data.put("end_date", jobOffer.getEnd_date().toString());
                data.put("salary_hour", Double.toString(jobOffer.getSalary_hour()));
                data.put("active", jobOffer.getActive());
                return data;
            }
        };
        // Se inicializa el objeto RequestQueue
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        // Se anade la peticion al objeto RequestQueue
        requestQueue.add(stringRequest);
    }

    /**
     * Metodo que permite volver a la pantalla de Mis Trabajos
     */

    public void toMyJobs() {
        Intent intent = new Intent(this, MyJobs.class);
        startActivity(intent);
        finish();
    }
}
