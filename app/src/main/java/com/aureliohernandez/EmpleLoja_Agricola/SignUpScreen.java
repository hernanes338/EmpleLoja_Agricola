package com.aureliohernandez.EmpleLoja_Agricola;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;



public class SignUpScreen extends AppCompatActivity {
    private Button signUpButton;
    private EditText email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_screen);
        signUpButton = (Button) findViewById(R.id.signUpButton);
        email = (EditText) findViewById(R.id.email);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        });
    }

    public void signUp(){
        boolean accepted = false;
        new AlertDialog.Builder(this)
                .setTitle("Creacion de tu cuenta")
                .setMessage("Se va a crear una cuenta asociada a la siguiente direccion de correo electronico: " + email.getText().toString() + "\n"
                + "Desea continuar?")
                .setIcon(android.R.drawable.ic_dialog_info)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        Toast.makeText(SignUpScreen.this, "Cuenta creada", Toast.LENGTH_SHORT).show();
                            returnToLogInScreen();
                    }})
                .setNegativeButton(android.R.string.no, null).show();

    }

    public void returnToLogInScreen() {
        Intent intent = new Intent(this, LogInScreen.class);
        startActivity(intent);
    }
}