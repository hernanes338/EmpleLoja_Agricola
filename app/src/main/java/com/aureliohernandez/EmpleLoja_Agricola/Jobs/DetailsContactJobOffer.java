package com.aureliohernandez.EmpleLoja_Agricola.Jobs;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
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

import com.aureliohernandez.EmpleLoja_Agricola.MainActivity;
import com.aureliohernandez.EmpleLoja_Agricola.Model.User;
import com.aureliohernandez.EmpleLoja_Agricola.R;
import com.aureliohernandez.EmpleLoja_Agricola.Users.UserLocalStore;

/**
 * @class
 * Clase que permite a un demandante de trabajo visualizar los detalles de una oferta de trabajo
 * con el fin de contactar con el ofertante en caso de estar interesado
 */
public class DetailsContactJobOffer extends AppCompatActivity {
    private static final int REQUEST_CALL = 1;
    private String title, description, startDate, endDate, salaryHour, phone;
    private ImageButton phoneCallButton, sendSmsButton;
    private UserLocalStore userLocalStore;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_offer_details_contact_screen);

        // obtencion de los detalles del usuario logueado
        userLocalStore = new UserLocalStore(this);
        user = userLocalStore.getLoggedInUser();

        //
        Toolbar toolbar = findViewById(R.id.toolbar);
        // Mostrar barra superior
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        // Mostrar el boton de flecha hacia detras
        actionBar.setDisplayHomeAsUpEnabled(true);
        // Mostrar texto en la barra superior
        actionBar.setTitle("Detalles de la oferta");

        title = getIntent().getStringExtra("Title");
        description = getIntent().getStringExtra("Description");
        startDate = getIntent().getStringExtra("Start_Date");
        endDate = getIntent().getStringExtra("End_Date");
        salaryHour = getIntent().getStringExtra("Salary_Hour");
        phone = getIntent().getStringExtra("Phone");

        TextView titleTextView = findViewById(R.id.titletextView);
        TextView descriptionTextView = findViewById(R.id.descriptionTextView);
        TextView startDateTextView = findViewById(R.id.startDateTextView);
        TextView endDateTextView = findViewById(R.id.endDateTextView);
        TextView salaryHourTextView = findViewById(R.id.salaryHourTextView);

        titleTextView.setText(title);
        descriptionTextView.setText(description);
        startDateTextView.setText(startDate);
        endDateTextView.setText(endDate);
        salaryHourTextView.setText(salaryHour);


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

    // El evento de item seleccionado (barra hacia detras) permite volver a la pantalla de login
    // al  pulsar el boton home (flecha hacia detras)
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                toMainScreen();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Metodo que permite volver a la pantalla principal
     */
    public void toMainScreen() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * Metodo para efectuar una llamada al numero de telefono del usuario creador de la oferta de trabajo
     */
    public void phoneCall () {
        String number = phone;
        if (number.trim().length() > 0) {
            if (ContextCompat.checkSelfPermission(DetailsContactJobOffer.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(DetailsContactJobOffer.this,
                        new String[] {Manifest.permission.CALL_PHONE},REQUEST_CALL);
            } else {
                String dial = "tel:" + number;
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
            }

    } else {
        Toast.makeText(DetailsContactJobOffer.this, "Telefono no disponible.", Toast.LENGTH_SHORT).show();
    }
    }


    /**
     * Gestion del permiso de llamada telefonica
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
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

    /**
     * Metodo para abrir la aplicacion de mensajeria por defecto al telefono del usuario creador de la oferta de trabajo
     * con un texto predeterminado el cual contiene el titulo de la oferta de trabajo a la que se quiere aplicar
     * y el telefono del usuario aplicante
     */
    public void sendSms () {
        String smsTo = phone;
        String jobOfferTitle = title;
        String smsFrom = String.valueOf(userLocalStore.getLoggedInUser().getPhone());
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", smsTo, null));
        intent.putExtra("sms_body", "Hola! Estoy interesado en la oferta de trabajo: " + jobOfferTitle
                + ".\nPuede contactar conmigo en el telefono " + smsFrom + "\nSaludos!");
        startActivity(intent);
    }

}
