package com.aureliohernandez.EmpleLoja_Agricola;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class JobDemandDetailsContact extends AppCompatActivity {
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
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

    }

    public void sendSms () {

    }
}
