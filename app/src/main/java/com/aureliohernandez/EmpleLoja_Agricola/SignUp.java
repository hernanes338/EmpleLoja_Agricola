package com.aureliohernandez.EmpleLoja_Agricola;

import android.content.Intent;
import android.os.Bundle;
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
import com.aureliohernandez.EmpleLoja_Agricola.Model.User;

import java.util.HashMap;
import java.util.Map;

public class SignUp extends AppCompatActivity {
    private String URL = "http://192.168.0.15/EmpleLoja_Agricola/php_scripts/signup.php";
    private EditText nameField, surnameField, phoneField, emailField, passwordField, repasswordField;
    private String name, surname, email, password, repassword;
    private int phone, role_id;
    private RadioGroup roleField;
    private Button signUpButton;
    private User user;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_screen);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Introduzca sus datos");

        nameField = (EditText) findViewById(R.id.name);
        surnameField = (EditText) findViewById(R.id.surname);
        phoneField = (EditText) findViewById(R.id.phone);
        emailField = (EditText) findViewById(R.id.email);
        passwordField = (EditText) findViewById(R.id.password);
        repasswordField = (EditText) findViewById(R.id.rePassword);

        name = surname = email = password = repassword = "";
        phone = role_id = 0;

        roleField = (RadioGroup) findViewById(R.id.radioGroupRole);

        roleField.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.radioButtonOffer:
                        role_id = 1;
                        break;
                    case R.id.radioButtonDemand:
                        role_id = 2;
                        break;
                }
            }
        });

        signUpButton = (Button) findViewById(R.id.signUpButton);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = nameField.getText().toString().trim();
                surname = surnameField.getText().toString().trim();
                phone = Integer.parseInt(phoneField.getText().toString().trim());
                email = emailField.getText().toString().trim();
                password = passwordField.getText().toString().trim();
                repassword = repasswordField.getText().toString().trim();

                if (!name.equals("") && !surname.equals("") && phone != 0 && !email.equals("") && !password.equals("") && !repassword.equals("") && role_id != 0) {
                    if(!password.equals(repassword)) {
                        Toast.makeText(SignUp.this, "La contraseña no coincide", Toast.LENGTH_SHORT).show();
                    } else {
                        user = new User(name, surname, phone, email, password, role_id);
                        signUp();
                    }
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
                toLogInScreen();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void signUp() {
                StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.equals("success")) {
                            Toast.makeText(SignUp.this, "Cuenta de usuario creada", Toast.LENGTH_SHORT).show();
                            toLogInScreen();
                        } else if (response.equals("failure")) {
                            Toast.makeText(SignUp.this, "La cuenta de usuario no ha sido creada", Toast.LENGTH_SHORT).show();
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
                        data.put("name", user.getName());
                        data.put("surname", user.getSurname());
                        data.put("phone", String.valueOf(user.getPhone()));
                        data.put("email", user.getEmail());
                        data.put("password", user.getPassword());
                        data.put("role_id", String.valueOf(user.getRole_id()));
                        return data;
                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                requestQueue.add(stringRequest);
            }


    public void toLogInScreen() {
        Intent intent = new Intent(this, LogIn.class);
        startActivity(intent);
        finish();
    }

}