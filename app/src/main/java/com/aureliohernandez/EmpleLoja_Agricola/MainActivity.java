package com.aureliohernandez.EmpleLoja_Agricola;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.aureliohernandez.EmpleLoja_Agricola.Jobs.AddJobDemand;
import com.aureliohernandez.EmpleLoja_Agricola.Jobs.AddJobOffer;
import com.aureliohernandez.EmpleLoja_Agricola.Jobs.DetailsContactJobDemand;
import com.aureliohernandez.EmpleLoja_Agricola.Jobs.DetailsContactJobOffer;
import com.aureliohernandez.EmpleLoja_Agricola.Jobs.MyJobs;
import com.aureliohernandez.EmpleLoja_Agricola.Jobs.RecyclerViewJobDemand;
import com.aureliohernandez.EmpleLoja_Agricola.Jobs.RecyclerViewJobDemandInterface;
import com.aureliohernandez.EmpleLoja_Agricola.Jobs.RecyclerViewJobOffer;
import com.aureliohernandez.EmpleLoja_Agricola.Jobs.RecyclerViewJobOfferInterface;
import com.aureliohernandez.EmpleLoja_Agricola.Model.JobDemand;
import com.aureliohernandez.EmpleLoja_Agricola.Model.JobOffer;
import com.aureliohernandez.EmpleLoja_Agricola.Model.User;
import com.aureliohernandez.EmpleLoja_Agricola.Users.LogIn;
import com.aureliohernandez.EmpleLoja_Agricola.Users.UserLocalStore;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Date;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity implements RecyclerViewJobOfferInterface, RecyclerViewJobDemandInterface {

    private User user;
    private JobOffer jobOffer;
    private JobDemand jobDemand;
    private UserLocalStore userLocalStore;
    private ArrayList<JobOffer> jobOffers = new ArrayList<>();
    private ArrayList<JobDemand> jobDemands = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializacion del objeto UserLocalStore con el contexto de la aplicacion
        userLocalStore = new UserLocalStore(this);
        // Inicializacion del objeto User con los datos guardados en SharedPreferences
        user = userLocalStore.getLoggedInUser();

        Toolbar toolbar = findViewById(R.id.toolbar);
        // Mostrar barra superior
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        // Mostrar texto en la barra superior
        actionBar.setTitle("¡Bienvenido " + user.getName() + "!");

        // Comprobacion del rol del usuario logueado para
        // obtener la informacion adecuada desde base de datos

        if(user.getRole_id() == 2) { // 2 Usuario Ofertante
            fetchJobDemands();
        } else if (user.getRole_id() == 3) { // 3 Usuario Demandante
            fetchJobOffers();
        } else {
            Toast.makeText(getApplicationContext(), "Tipo de usuario incorrecto", Toast.LENGTH_SHORT).show();
        }

        // Permite obtener los resultados de base de datos
        // anter de renderizar los elementos de la lista
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if(user.getRole_id() == 2) {
            RecyclerView recyclerView = findViewById(R.id.recyclerView);

            RecyclerViewJobDemand adapter = new RecyclerViewJobDemand(this, jobDemands, this);

            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        } else if (user.getRole_id() == 3) {
            RecyclerView recyclerView = findViewById(R.id.recyclerView);

            RecyclerViewJobOffer adapter = new RecyclerViewJobOffer(this, jobOffers, this);

            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        } else {
            Toast.makeText(getApplicationContext(), "Tipo de usuario incorrecto", Toast.LENGTH_SHORT).show();
        }

        // Crea la barra de navegacion inferior de la vista principal
        BottomNavigationView bottomNavigationView = (BottomNavigationView)findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    // Funcionalidad de Refrescar la informacion recibida desde base de datos no desarrollada
                    case R.id.refresh:
                        if (user.getRole_id() == 2) {
                            Toast.makeText(getApplicationContext(), "Funcionalidad no disponible", Toast.LENGTH_SHORT).show();
                        } else if (user.getRole_id() == 3) {
                            Toast.makeText(getApplicationContext(), "Funcionalidad no disponible", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Tipo de usuario incorrecto", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    // Comprobacion del rol del usuario logueado para
                    // iniciar la Activity adecuada
                    case R.id.addJob:
                        if (user.getRole_id() == 2) { // 2 Usuario Ofertante
                            toAddJobOfferActivity();
                        } else if (user.getRole_id() == 3) { // 3 Usuario Demandante
                            toAddJobDemandActivity();
                        } else {
                            Toast.makeText(getApplicationContext(), "Tipo de usuario incorrecto", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    // Inicio de la Activity de trabajos creados por el usuario logueado
                    case R.id.myJobs:
                        toMyJobOffersActivity();
                }
                return false;
            }
        });

    }

    // Muestra el boton de menu de opciones en la barra superior
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    // Evalua la opcion seleccionada dentro del menu de opciones
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            // Opcion desloguearse deja en blanco el archivo SharedPreferences
            // y lleva al usuario a la pantalla de log in
            case R.id.logOut:
                userLocalStore.clearUserData();
                toLogInActivity();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Metodo que permite almacenar en un array de tipo JobOffer la informacion
     * de todas las ofertas de trabajo activas existentes en la tabla job_offers
     */
    public void fetchJobOffers() {
        // Se inicializa el objeto RequestQueue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        // Peticion de un String desde la URL
        final StringRequest stringRequest = new StringRequest(Request.Method.GET, URLManagement.URL_FETCH_JOB_OFFERS, new Response.Listener<String>() {
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
                        int phone = jsonObject.getInt("PHONE");
                        // Creacion del objeto JobDemand
                        jobOffer = new JobOffer(id, title, description, user_id, start_date, end_date, salary_hour, active, phone);
                        // Se anade el objeto anterior al ArrayList de objetos JobOffer
                        jobOffers.add(jobOffer);

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("Response failed: " + error);
            }
        });
        // Se anade la peticion al objeto RequestQueue
        requestQueue.add(stringRequest);

    }

    /**
     * Metodo que permite almacenar en un array de tipo JobDemand la informacion
     * de todas las demandas de trabajo activas existentes en la tabla job_demands
     */
    public void fetchJobDemands() {
        // Se inicializa el objeto RequestQueue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        // Peticion de un String desde la URL
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLManagement.URL_FETCH_JOB_DEMANDS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response); // Se crea un array JSON con la respuesta

                    for (int i = 0; i < jsonArray.length(); i++) { // Recorrido por los elementos del array JSON para obtener los atributos

                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        int id = jsonObject.getInt("ID");
                        String title = jsonObject.getString("TITLE");
                        String description = jsonObject.getString("DESCRIPTION");
                        int user_id = jsonObject.getInt("USER_ID");
                        Date available_from = Date.valueOf(jsonObject.getString("AVAILABLE_FROM"));
                        String active = jsonObject.getString("ACTIVE");
                        int phone = jsonObject.getInt("PHONE");
                        // Creacion del objeto JobDemand
                        jobDemand = new JobDemand(id, title, description, user_id, available_from, active, phone);
                        // Se anade el objeto anterior al ArrayList de objetos JobDemand
                        jobDemands.add(jobDemand);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("Response failed: " + error);
            }
        });
        // Se anade la peticion al objeto RequestQueue
        requestQueue.add(stringRequest);

    }

    /**
     * Metodo que permite acceder a la pantalla de log in
     */
    public void toLogInActivity(){
        Intent intent = new Intent(this, LogIn.class);
        startActivity(intent);
        finish();
    }

    /**
     * Metodo que permite acceder a la pantalla de anadir ofertas de trabajo
     */
    public void toAddJobOfferActivity(){
        Intent intent = new Intent(this, AddJobOffer.class);
        startActivity(intent);
        finish();
    }

    /**
     * Metodo que permite acceder a la pantalla de anadir demandas de trabajo
     */
    public void toAddJobDemandActivity(){
        Intent intent = new Intent(this, AddJobDemand.class);
        startActivity(intent);
        finish();
    }

    /**
     * Metodo que permite acceder a la pantalla de trabajos propios
     */
    public void toMyJobOffersActivity(){
        Intent intent = new Intent(this, MyJobs.class);
        startActivity(intent);
        finish();
    }


    @Override
    public void onItemClick(int position) {

        if (user.getRole_id() == 2) {
            Intent intent = new Intent (MainActivity.this, DetailsContactJobDemand.class);
            intent.putExtra("Job_Demand_Id", String.valueOf(jobDemands.get(position).getDemand_id()));
            intent.putExtra("Title", jobDemands.get(position).getTitle());
            intent.putExtra("Description", jobDemands.get(position).getDescription());
            intent.putExtra("Available_From", String.valueOf(jobDemands.get(position).getAvailable_from()));
            intent.putExtra("User_id", String.valueOf(jobDemands.get(position).getUser_id()));
            intent.putExtra("Active", String.valueOf(jobDemands.get(position).getActive()));
            intent.putExtra("Phone", String.valueOf(jobDemands.get(position).getPhone()));

            startActivity(intent);

        } else if (user.getRole_id() == 3) {
            Intent intent = new Intent (MainActivity.this, DetailsContactJobOffer.class);
            intent.putExtra("Job_Offer_Id", String.valueOf(jobOffers.get(position).getOffer_id()));
            intent.putExtra("Title", jobOffers.get(position).getTitle());
            intent.putExtra("Description", jobOffers.get(position).getDescription());
            intent.putExtra("Start_Date", String.valueOf(jobOffers.get(position).getStart_date()));
            intent.putExtra("End_Date", String.valueOf(jobOffers.get(position).getEnd_date()));
            intent.putExtra("Salary_Hour", String.valueOf(jobOffers.get(position).getSalary_hour()));
            intent.putExtra("User_id", String.valueOf(jobOffers.get(position).getUser_id()));
            intent.putExtra("Active", String.valueOf(jobOffers.get(position).getActive()));
            intent.putExtra("Phone", String.valueOf(jobOffers.get(position).getPhone()));
            startActivity(intent);

        } else {
            Toast.makeText(getApplicationContext(), "Tipo de usuario incorrecto", Toast.LENGTH_SHORT).show();
        }

    }
}