package com.aureliohernandez.EmpleLoja_Agricola;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;


    public class LogIn extends AppCompatActivity {

        private final String URL = "http://192.168.0.15/EmpleLoja_Agricola/php_scripts/login.php";
        private EditText emailField, passwordField;
        private String email, password;
        private Button logInButton, signupButton;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            setTheme(R.style.Theme_EmpleLojaAgricola);
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_log_in_screen);
            SharedPreferences sharedPref = getSharedPreferences("LogIn", Context.MODE_PRIVATE);
            Boolean loginStatus = sharedPref.getBoolean("loginStatus", false);
            if (loginStatus) {
                toMainActivity();
            }

            email = password = "";
            emailField = findViewById(R.id.username);
            passwordField = findViewById(R.id.password);

            logInButton = (Button) findViewById(R.id.logInButton);

            logInButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    logIn();
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

        public void logIn() {
            email = emailField.getText().toString().trim();
            password = passwordField.getText().toString().trim();
            if(!email.equals("") && !password.equals("")){
                StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("res", response);
                        if (response.equals("success")) {
                            SharedPreferences sharedPref = getSharedPreferences("LogIn", Context.MODE_PRIVATE);
                            SharedPreferences.Editor edit = sharedPref.edit();
                            edit.putBoolean("loginStatus", true);
                            edit.commit();
                            toMainActivity();
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
                        data.put("email", email);
                        data.put("password", password);
                        return data;
                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                requestQueue.add(stringRequest);
            }else{
                Toast.makeText(this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
            }
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
    }

