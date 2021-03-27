package com.example.hackatonv1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.media.Image;
import android.os.Bundle;
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
        setContentView(R.layout.activity_main);

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

}