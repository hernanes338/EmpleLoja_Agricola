package com.aureliohernandez.EmpleLoja_Agricola;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class JobDemandDetailsContact extends AppCompatActivity {
    private static final int REQUEST_CALL = 1;
    private String title, description, availableFrom;
    private ImageButton phoneCallButton, sendSmsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_demand_details_contact_screen);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Detalles de la demanda");

        title = getIntent().getStringExtra("Title");
        description = getIntent().getStringExtra("Description");
        availableFrom = getIntent().getStringExtra("Available_From");

        TextView titleTextView = findViewById(R.id.titletextView);
        TextView descriptionTextView = findViewById(R.id.descriptionTextView);
        TextView startDateTextView = findViewById(R.id.availableFromTextView);

        titleTextView.setText(title);
        descriptionTextView.setText(description);
        startDateTextView.setText(availableFrom);


        phoneCallButton = (ImageButton) findViewById(R.id.callButton);

        phoneCallButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phoneCall ();
            }
        });

        sendSmsButton = (ImageButton) findViewById(R.id.smsButton);

        sendSmsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendSms ();
            }
        });
    }

    // this event will enable the back
    // function to the button on press
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                toMainScreen();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void toMainScreen() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void phoneCall () {
        String number = "667019420";
        if (number.trim().length() > 0) {
            if (ContextCompat.checkSelfPermission(JobDemandDetailsContact.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(JobDemandDetailsContact.this,
                        new String[] {Manifest.permission.CALL_PHONE},REQUEST_CALL);
            } else {
                String dial = "tel:" + number;
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
            }

        } else {
            Toast.makeText(JobDemandDetailsContact.this, "Telefono no disponible.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CALL) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                phoneCall();
            } else {
                Toast.makeText(this, "Permiso no concedido.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void sendSms () {

    }
}
