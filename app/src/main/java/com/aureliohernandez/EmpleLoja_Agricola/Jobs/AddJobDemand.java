package com.aureliohernandez.EmpleLoja_Agricola.Jobs;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
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
import com.aureliohernandez.EmpleLoja_Agricola.MainActivity;
import com.aureliohernandez.EmpleLoja_Agricola.Model.JobDemand;
import com.aureliohernandez.EmpleLoja_Agricola.Model.User;
import com.aureliohernandez.EmpleLoja_Agricola.R;
import com.aureliohernandez.EmpleLoja_Agricola.URLManagement;
import com.aureliohernandez.EmpleLoja_Agricola.Users.UserLocalStore;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @class
 * Clase que permite a un usuario con rol demandante anadir una demanda de trabajo
 * a la base de datos de la aplicacion
 */

public class AddJobDemand extends AppCompatActivity {

    private User user;
    private UserLocalStore userLocalStore;
    private EditText titleField, descriptionField, availableFromField;
    private String title, description;
    private int user_id;
    private Date availableFrom;
    private Button addJobDemandButton;
    private JobDemand jobDemand;

    Calendar calendar;
    EditText clickedEditText; // objeto generico EditText para reutilizar en varios campos

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_job_demand_screen);
        Toolbar toolbar = findViewById(R.id.toolbar);
        // Mostrar barra superior
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);
        // Mostrar texto en la barra superior
        actionBar.setTitle("Detalles de la demanda");

        userLocalStore = new UserLocalStore(this);
        user = userLocalStore.getLoggedInUser();

        titleField = findViewById(R.id.title);
        descriptionField = findViewById(R.id.description);
        availableFromField = findViewById(R.id.availableFromDate);

        addJobDemandButton = findViewById(R.id.addJobDemandButton);

        addJobDemandButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title = titleField.getText().toString().trim();
                description = descriptionField.getText().toString().trim();
                user_id = user.getUser_id();
                availableFrom = Date.valueOf(availableFromField.getText().toString().trim());

                jobDemand = new JobDemand(title, description, user_id, availableFrom);
                addJobDemand();
            }
        });

        // Creacion de calendario para usar en campos de tipo fecha
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
                String Format = "yyyy-MM-dd"; // formato del tipo de dato DATE de la base de datos
                SimpleDateFormat sdf = new SimpleDateFormat(Format, Locale.ITALY);
                clickedEditText.setText(sdf.format(calendar.getTime())); // objeto generico EditText
            }
        };

        availableFromField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickedEditText = (EditText) v; // objeto generico EditText
                new DatePickerDialog(AddJobDemand.this, date, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    // El evento de item seleccionado (barra hacia detras) permite volver a la pantalla de login
    // al  pulsar el boton home (flecha hacia detras)
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                toMainScreen();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Metodo que permite anadir una demanda de trabajo a la base de datos.
     *
     */
    public void addJobDemand() {
        // Peticion de un String desde la URL
        final StringRequest stringRequest = new StringRequest(Request.Method.POST, URLManagement.URL_ADD_JOB_DEMAND, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) { // En caso de respuesta valida se evalua el String devuelto
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

        // Se inicializa el objeto RequestQueue
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        // Se anade la peticion al objeto RequestQueue
        requestQueue.add(stringRequest);
    }

    /**
     * Metodo que permite volver a la pantalla principal
     */
    public void toMainScreen() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
