package com.example.hackatonv1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.PendingIntent;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.widget.ImageButton;
import android.support.v4.app.*;
import android.widget.*;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.List;

import static com.google.android.gms.location.Geofence.NEVER_EXPIRE;

import static com.example.hackatonv1.Globals.getLang;
import static com.example.hackatonv1.Globals.setLang;

public class MainActivity extends AppCompatActivity {
    Context context;
    Resources resources;
    String currentStatue;
    PendingIntent geofencePendingIntent;
    private GeofencingClient geofencingClient;
    private List<Geofence> geofenceList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createNotificationChannel();
        setContentView(R.layout.activity_main);
        geofencingClient = LocationServices.getGeofencingClient(this);
        geofenceList.add(new Geofence.Builder().setRequestId("geofence1").setCircularRegion(50.869818657388684, 4.716648173905761, 10).setExpirationDuration(NEVER_EXPIRE)
                .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER |
                        Geofence.GEOFENCE_TRANSITION_EXIT)
                .build());
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            geofencingClient.addGeofences(getGeofencingRequest(), getGeofencePendingIntent())
                    .addOnSuccessListener(this, aVoid -> {
                        // Geofences added
                        // ...
                    })
                    .addOnFailureListener(this, e -> {
                        // Failed to add geofences
                        // ...
                    });
            return;
        }

        Intent intent = new Intent(MainActivity.this, NotificationMessage.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, intent, 0);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        long timeAtButtonClick = System.currentTimeMillis();
        long tenSecondsInMillis = 1000 * 10;
        alarmManager.set(AlarmManager.RTC_WAKEUP, timeAtButtonClick+tenSecondsInMillis, pendingIntent);




        ImageView secretBtn = (ImageView) findViewById(R.id.imageView);
        secretBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, InfoPage.class));
            }
        });

        ImageButton btnEng = (ImageButton) findViewById(R.id.englishSelect);
        btnEng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context = LocaleHelper.setLocale(MainActivity.this, "en");
                resources = context.getResources();
                setLang("en");
                startActivity(new Intent(MainActivity.this, GoogleMapsActivity.class));
            }
        });
        ImageButton btnNL = (ImageButton) findViewById(R.id.dutchSelect);
        btnNL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context = LocaleHelper.setLocale(MainActivity.this, "nl");
                resources = context.getResources();
                setLang("nl");
                startActivity(new Intent(MainActivity.this, GoogleMapsActivity.class));

            }
        });
        ImageButton btnFR = (ImageButton) findViewById(R.id.frenchSelect);
        btnFR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context = LocaleHelper.setLocale(MainActivity.this, "fr");
                resources = context.getResources();
                setLang("fr");
                startActivity(new Intent(MainActivity.this, GoogleMapsActivity.class));
            }
        });
        ImageButton btnSp = (ImageButton) findViewById(R.id.spanishSelect);
        btnSp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context = LocaleHelper.setLocale(MainActivity.this, "es");
                resources = context.getResources();
                setLang("es");
                startActivity(new Intent(MainActivity.this, GoogleMapsActivity.class));
            }
        });
    }


    private PendingIntent getGeofencePendingIntent() {
        // Reuse the PendingIntent if we already have it.
        if (geofencePendingIntent != null) {
            return geofencePendingIntent;
        }
        Intent intent = new Intent(this, GeofenceBroadcastReceiver.class);
        // We use FLAG_UPDATE_CURRENT so that we get the same pending intent back when
        // calling addGeofences() and removeGeofences().
        geofencePendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.
                FLAG_UPDATE_CURRENT);
        return geofencePendingIntent;
    }
    private GeofencingRequest getGeofencingRequest() {
        GeofencingRequest.Builder builder = new GeofencingRequest.Builder();
        builder.setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER);
        builder.addGeofences(geofenceList);
        return builder.build();
    }

    private void createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name = "UserNotificationChannel";
            String description = "informing users with historical knowledge";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("notifyUsers", name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

}