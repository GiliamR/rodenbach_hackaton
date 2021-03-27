package com.example.hackatonv1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.widget.ImageButton;
import android.support.v4.app.*;
import android.widget.*;

import static com.example.hackatonv1.Globals.getLang;
import static com.example.hackatonv1.Globals.setLang;

public class MainActivity extends AppCompatActivity {
    Context context;
    Resources resources;
    String currentStatue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createNotificationChannel();
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(MainActivity.this, NotificationMessage.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, intent, 0);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        long timeAtButtonClick = System.currentTimeMillis();
        long tenSecondsInMillis = 1000 * 10;
        alarmManager.set(AlarmManager.RTC_WAKEUP, timeAtButtonClick+tenSecondsInMillis, pendingIntent);


        ImageButton btnEng = (ImageButton) findViewById(R.id.englishSelect);
        btnEng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context = LocaleHelper.setLocale(MainActivity.this, "en");
                resources = context.getResources();
                setLang("en");
                startActivity(new Intent(MainActivity.this, SelectionActivity.class));
            }
        });
        ImageButton btnNL = (ImageButton) findViewById(R.id.dutchSelect);
        btnNL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context = LocaleHelper.setLocale(MainActivity.this, "nl");
                resources = context.getResources();
                setLang("nl");
                startActivity(new Intent(MainActivity.this, SelectionActivity.class));

            }
        });
        ImageButton btnFR = (ImageButton) findViewById(R.id.frenchSelect);
        btnFR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context = LocaleHelper.setLocale(MainActivity.this, "fr");
                resources = context.getResources();
                setLang("fr");
                startActivity(new Intent(MainActivity.this, SelectionActivity.class));
            }
        });
        ImageButton btnSp = (ImageButton) findViewById(R.id.spanishSelect);
        btnSp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context = LocaleHelper.setLocale(MainActivity.this, "es");
                resources = context.getResources();
                setLang("es");
                startActivity(new Intent(MainActivity.this, SelectionActivity.class));
            }
        });
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