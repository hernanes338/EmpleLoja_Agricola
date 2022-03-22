package com.aureliohernandez.EmpleLoja_Agricola;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SignUpScreen extends AppCompatActivity {
    private Button signUpButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_screen);
        signUpButton = (Button) findViewById(R.id.signUpButton);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        });
    }

    public void signUp(){
        Intent intent = new Intent(this, LogInScreen.class);
        startActivity(intent);
    }
}