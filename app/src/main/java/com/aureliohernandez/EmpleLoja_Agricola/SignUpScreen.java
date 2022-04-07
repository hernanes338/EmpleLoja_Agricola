package com.aureliohernandez.EmpleLoja_Agricola;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.vishnusivadas.advanced_httpurlconnection.PutData;


public class SignUpScreen extends AppCompatActivity {
    private Button signUpButton;
    private EditText nameField, surnameField, phoneField, emailField, passwordField;
    private RadioGroup roleField;
    private String roleValue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_screen);

        nameField = (EditText) findViewById(R.id.name);
        surnameField = (EditText) findViewById(R.id.surname);
        phoneField = (EditText) findViewById(R.id.phone);
        emailField = (EditText) findViewById(R.id.email);
        passwordField = (EditText) findViewById(R.id.password);
        roleField = (RadioGroup) findViewById(R.id.radioGroupRole);

        roleField.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.radioButtonOffer:
                        roleValue = "1";
                        break;
                    case R.id.radioButtonDemand:
                        roleValue = "2";
                        break;
                }
            }
        });

        signUpButton = (Button) findViewById(R.id.signUpButton);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name, surname, phone, email, password, role;
                name = String.valueOf(nameField.getText());
                surname = String.valueOf(surnameField.getText());
                phone = String.valueOf(phoneField.getText());
                email = String.valueOf(emailField.getText());
                password = String.valueOf(passwordField.getText());
                role = roleValue;



                // Start ProgressBar first (Set visibility VISIBLE)
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        //Starting Write and Read data with URL
                        //Creating array for parameters
                        String[] field = new String[6];
                        field[0] = "NAME";
                        field[1] = "SURNAME";
                        field[2] = "PHONE";
                        field[3] = "EMAIL";
                        field[4] = "PASSWORD";
                        field[5] = "ROLE";
                        //Creating array for data
                        String[] data = new String[6];
                        data[0] = name;
                        data[1] = surname;
                        data[2] = phone;
                        data[3] = email;
                        data[4] = password;
                        data[5] = role;


                        PutData putData = new PutData("http://192.168.0.15/EmpleLoja_Agricola/signup.php", "POST", field, data);
                        if (putData.startPut()) {
                            if (putData.onComplete()) {
                                String result = putData.getResult();
                                if(result.equals("Sign Up Success")) {
                                    signUp();
                                }
                                else {
                                    Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    }
                });
            }
        });
    }

    public void signUp(){
        boolean accepted = false;
        new AlertDialog.Builder(this)
                .setTitle("Creacion de tu cuenta")
                .setMessage("Se va a crear una cuenta asociada a la siguiente direccion de correo electronico: " + emailField.getText().toString() + "\n"
                + "Desea continuar?")
                .setIcon(android.R.drawable.ic_dialog_info)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        Toast.makeText(SignUpScreen.this, "Cuenta creada", Toast.LENGTH_SHORT).show();
                            toLogInScreen();
                    }})
                .setNegativeButton(android.R.string.no, null).show();

    }

    public void toLogInScreen() {
        Intent intent = new Intent(this, LogInScreen.class);
        startActivity(intent);
        finish();
    }
}