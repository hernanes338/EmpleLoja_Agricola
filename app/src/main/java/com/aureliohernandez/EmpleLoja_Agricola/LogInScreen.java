package com.aureliohernandez.EmpleLoja_Agricola;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LogInScreen extends AppCompatActivity {
    private TextView username;

    private TextView password;

    private Button logInButton;

    private Button signUpActivityButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setTheme(R.style.Theme_EmpleLojaAgricola);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in_screen);

        username = (TextView) findViewById(R.id.username);
        password = (TextView) findViewById(R.id.password);

        logInButton = (Button) findViewById(R.id.logInButton);

        logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logIn();
            }
        });

        signUpActivityButton = (Button) findViewById(R.id.signUpActivityButton);

        signUpActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                signUpActivity();
            }
        });

    }

    public void logIn(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void signUpActivity(){
        Intent intent = new Intent(this, SignUpScreen.class);
        startActivity(intent);
    }


    }
