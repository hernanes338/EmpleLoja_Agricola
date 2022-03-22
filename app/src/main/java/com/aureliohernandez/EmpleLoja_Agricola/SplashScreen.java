package com.aureliohernandez.EmpleLoja_Agricola;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SplashScreen extends AppCompatActivity {
    private Button startAppButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        startAppButton = (Button) findViewById(R.id.startAppButton);

        startAppButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startApp();
            }
        });

    }

    public void startApp(){
        Intent intent = new Intent(this, LogInScreen.class);
        startActivity(intent);
    }
}