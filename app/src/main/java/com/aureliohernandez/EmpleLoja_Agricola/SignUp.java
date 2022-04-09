package com.aureliohernandez.EmpleLoja_Agricola;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
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

public class SignUp extends AppCompatActivity {
    private String URL = "http://192.168.0.15/EmpleLoja_Agricola/php_scripts/signup.php";
    private EditText nameField, surnameField, phoneField, emailField, passwordField, repasswordField;
    private String name, surname, phone, email, password, repassword, role;
    private RadioGroup roleField;
    private Button signUpButton;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_screen);
        nameField = (EditText) findViewById(R.id.name);
        surnameField = (EditText) findViewById(R.id.surname);
        phoneField = (EditText) findViewById(R.id.phone);
        emailField = (EditText) findViewById(R.id.email);
        passwordField = (EditText) findViewById(R.id.password);
        repasswordField = (EditText) findViewById(R.id.rePassword);

        name = surname = phone = email = password = repassword = role = "";

        roleField = (RadioGroup) findViewById(R.id.radioGroupRole);

        roleField.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.radioButtonOffer:
                        role = "1";
                        break;
                    case R.id.radioButtonDemand:
                        role = "2";
                        break;
                }
            }
        });

        signUpButton = (Button) findViewById(R.id.signUpButton);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        });

    }

    public void signUp() {
        name = nameField.getText().toString().trim();
        surname = surnameField.getText().toString().trim();
        phone = phoneField.getText().toString().trim();
        email = emailField.getText().toString().trim();
        password = passwordField.getText().toString().trim();
        repassword = repasswordField.getText().toString().trim();

        if (!name.equals("") && !surname.equals("") && !phone.equals("") && !email.equals("") && !password.equals("") && !repassword.equals("") && !role.equals("")) {
            if(!password.equals(repassword)) {
                Toast.makeText(SignUp.this, "La contrasena no coincide", Toast.LENGTH_SHORT).show();
            } else {
                StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.equals("success")) {
                            Toast.makeText(SignUp.this, "Cuenta creada", Toast.LENGTH_SHORT).show();
                            toLogInScreen();
                        } else if (response.equals("failure")) {
                            Toast.makeText(SignUp.this, "La cuenta no pudo ser creada", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.toString().trim(), Toast.LENGTH_SHORT).show();
                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> data = new HashMap<>();
                        data.put("name", name);
                        data.put("surname", surname);
                        data.put("phone", phone);
                        data.put("email", email);
                        data.put("password", password);
                        data.put("role", role);
                        return data;
                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                requestQueue.add(stringRequest);
            }
        } else{
            Toast.makeText(this, "Fields can not be empty!", Toast.LENGTH_SHORT).show();
        }


    }

    public void toLogInScreen() {
        Intent intent = new Intent(this, LogIn.class);
        startActivity(intent);
        finish();
    }

}