package com.aureliohernandez.EmpleLoja_Agricola.Users;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.aureliohernandez.EmpleLoja_Agricola.MainActivity;
import com.aureliohernandez.EmpleLoja_Agricola.Model.User;
import com.aureliohernandez.EmpleLoja_Agricola.R;
import com.aureliohernandez.EmpleLoja_Agricola.URLManagement;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


/**
 * @class Clase que permite autenticarse para acceder a la aplicacion introduciendo valores en
 * los campos mostrados en la vista
 * Ademas permite obtener los detalles del usuario que realiza el login y guardarlos
 * en un archivo SharedPreferences
 */

public class LogIn extends AppCompatActivity {

    private EditText emailField, passwordField;
    private String email, password;
    private Button logInButton, signupButton;
    private User user;
    private UserLocalStore userLocalStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_EmpleLojaAgricola);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in_screen);

        userLocalStore = new UserLocalStore(this);

        emailField = (EditText) findViewById(R.id.username);
        passwordField = (EditText) findViewById(R.id.password);

        email = password = "";

        logInButton = (Button) findViewById(R.id.logInButton);

        logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = emailField.getText().toString().trim();
                password = passwordField.getText().toString().trim();
                if (!email.equals("") && !password.equals("")) {
                    user = new User(email, password);
                    logIn();
                } else {
                    Toast.makeText(getApplicationContext(), "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
                }
            }
        });

        signupButton = (Button) findViewById(R.id.signUpActivityButton);

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toSignUpActivity();
            }
        });


    }

    /**
     * Al iniciar la pantalla comprueba si el usuario esta logueado.
     * De ser asi, se le permite acceder directamente a la seccion principal
     */

    @Override
    protected void onStart() {
        super.onStart();
        if (userLocalStore.getUserLogInStatus()) {
            toMainActivity();
        }
    }

    /**
     * Metodo que comprueba si el email y la contrasena coinciden con los valores almacenados
     * en base de datos. De ser asi, almacena los datos del usuario en un archivo SharedPreferences
     * y permite al usuario acceder a la seccion principal
     */
    public void logIn() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLManagement.URL_LOGIN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equals("success")) {
                    userLocalStore.setUserLoggedIn(true);
                    storeUserDetails();
                } else if (response.equals("failure")) {
                    Toast.makeText(getApplicationContext(), "Correo electrónico o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LogIn.this, error.toString().trim(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data = new HashMap<>();
                data.put("email", user.getEmail());
                data.put("password", user.getPassword());
                return data;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    /**
     * Metodo que permite obtener los detalles del usuario logueado y almacenarlos
     * en un archivo SharedPreferences.
     * Tras esto, el usuario accede a la pantalla principal
     */
    public void storeUserDetails() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLManagement.URL_USER_DETAILS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);

                    JSONObject jsonObject = jsonArray.getJSONObject(0);

                    int id = jsonObject.getInt("USER_ID");
                    String name = jsonObject.getString("NAME");
                    String surname = jsonObject.getString("SURNAME");
                    int phone = jsonObject.getInt("PHONE");
                    String email = jsonObject.getString("EMAIL");
                    String password = jsonObject.getString("PASSWORD");
                    int role = jsonObject.getInt("ROLE_ID");

                    user = new User(id, name, surname, phone, email, password, role);

                    userLocalStore.storeUserData(user);

                    System.out.println("Detalles del usuario guardados");

                    toMainActivity();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("Response failed: " + error);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data = new HashMap<>();
                data.put("email", user.getEmail());
                return data;
            }
        };

        requestQueue.add(stringRequest);

    }

    /**
     * Metodo que permite acceder a la pantalla de registro
     */
    public void toSignUpActivity() {
        Intent intent = new Intent(this, SignUp.class);
        startActivity(intent);
        finish();
    }

    /**
     * Metodo que permite acceder a la pantalla principal
     */
    public void toMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}

