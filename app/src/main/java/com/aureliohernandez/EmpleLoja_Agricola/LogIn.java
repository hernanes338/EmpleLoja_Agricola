package com.aureliohernandez.EmpleLoja_Agricola;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.aureliohernandez.EmpleLoja_Agricola.Model.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


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
                    if(!email.equals("") && !password.equals("")){
                        user = new User(email, password);
                        logIn();
                    }else{
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

        @Override
        protected void onStart() {
            super.onStart();
            if(userLocalStore.getUserLogInStatus()) {
                toMainActivity();
            }
        }

        public void logIn() {
            final String URL_LOGIN = "http://192.168.0.25/EmpleLoja_Agricola/php_scripts/login.php";
                StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_LOGIN, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("Res: ", response);
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
                }){
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


        public void toSignUpActivity(){
            Intent intent = new Intent(this, SignUp.class);
            startActivity(intent);
            finish();
        }

        public void toMainActivity(){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }



/*
        public void storeUserDetails() {
            String URL_USER_DETAILS = "http://192.168.0.25/EmpleLoja_Agricola/php_scripts/fetch_user_details.php";

            RequestQueue requestQueue = Volley.newRequestQueue(this);

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URL_USER_DETAILS, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        JSONArray jsonArray = response.getJSONArray("users");

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);

                            int id = jsonObject.getInt("USER_ID");
                            String name = jsonObject.getString("NAME");
                            String surname = jsonObject.getString("SURNAME");
                            int phone = jsonObject.getInt("PHONE");
                            String email = jsonObject.getString("EMAIL");
                            String  password = jsonObject.getString("PASSWORD");
                            int role = jsonObject.getInt("ROLE_ID");

                            user = new User(id, name, surname, phone, email, password, role);

                            userLocalStore.storeUserData(user);
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
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> data = new HashMap<>();
                    data.put("email", user.getEmail());
                    return data;
                }
            };

            requestQueue.add(jsonObjectRequest);

        }
 */

        public void storeUserDetails() {
            String URL_USER_DETAILS = "http://192.168.0.25/EmpleLoja_Agricola/php_scripts/fetch_user_details.php";

            RequestQueue requestQueue = Volley.newRequestQueue(this);

            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_USER_DETAILS, new Response.Listener<String>() {
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
                        String  password = jsonObject.getString("PASSWORD");
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
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> data = new HashMap<>();
                    data.put("email", user.getEmail());
                    return data;
                }
            };

            requestQueue.add(stringRequest);

        }

    }

