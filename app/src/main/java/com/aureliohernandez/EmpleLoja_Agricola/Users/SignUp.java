package com.aureliohernandez.EmpleLoja_Agricola.Users;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
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
import com.aureliohernandez.EmpleLoja_Agricola.R;
import com.aureliohernandez.EmpleLoja_Agricola.URLManagement;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @class Clase que permite crear una cuenta de usuario introduciendo valores en todos los campos
 * mostrados en la vista
 */

public class SignUp extends AppCompatActivity {

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
        // Mostrar barra superior
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        // Mostrar el boton de flecha hacia detras
        actionBar.setDisplayHomeAsUpEnabled(true);
        // Mostrar texto en la barra superior
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

        roleField.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radioButtonOffer:
                        role_id = 2;
                        break;
                    case R.id.radioButtonDemand:
                        role_id = 3;
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
                if (phoneField.getText().toString().trim().length() > 0) {
                    phone = Integer.parseInt(phoneField.getText().toString().trim());
                }
                email = emailField.getText().toString().trim();
                password = passwordField.getText().toString().trim();
                repassword = repasswordField.getText().toString().trim();

                if (!name.equals("") && !surname.equals("") && phone != 0 && !email.equals("")
                        && !password.equals("") && !repassword.equals("") && role_id != 0) {
                    if (!password.equals(repassword)) {
                        Toast.makeText(SignUp.this, "La contraseña no coincide", Toast.LENGTH_SHORT).show();
                    } else if (name.length() > 30) {
                        Toast.makeText(SignUp.this, "El límite de caracteres del campo Nombre es 30", Toast.LENGTH_SHORT).show();
                    } else if (surname.length() > 60) {
                        Toast.makeText(SignUp.this, "El límite de caracteres del campo Apellidos es 60", Toast.LENGTH_SHORT).show();
                    } else if (!isValidPhone(String.valueOf(phone))) {
                        Toast.makeText(SignUp.this, "El formato del campo Teléfono no es correcto", Toast.LENGTH_SHORT).show();
                    } else if (!isValidEmail(email)) {
                        Toast.makeText(SignUp.this, "El formato del campo Correo Electrónico no es correcto", Toast.LENGTH_SHORT).show();
                    } else {
                        // Si todos los campos contienen valores, se crea un objeto de tipo User
                        // con los valores recogidos de los campos de la vista
                        user = new User(name, surname, phone, email, password, role_id);
                        signUp();
                    }
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
                toLogInScreen();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Metodo que permite crear una cuenta de usuario recogiendo los valores del objeto de tipo User
     * creado a traves los campos de la vista
     * Si la creacion de la cuenta se lleva a cabo, el usuario vuelve a la pantalla de login
     */
    public void signUp() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLManagement.URL_SIGNUP, new Response.Listener<String>() {
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
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data = new HashMap<>();
                data.put("name", user.getName());
                data.put("surname", user.getSurname());
                data.put("phone", Integer.toString(user.getPhone()));
                data.put("email", user.getEmail());
                data.put("password", user.getPassword());
                data.put("role_id", Integer.toString(user.getRole_id()));
                return data;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    private boolean isValidEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }

    private boolean isValidPhone(String phone) {
        String regexStr = "^[0-9]{9}$";
        return phone.matches(regexStr);
    }

    /**
     * Metodo que permite volver a la pantalla de login
     */
    public void toLogInScreen() {
        Intent intent = new Intent(this, LogIn.class);
        startActivity(intent);
        finish();
    }

}