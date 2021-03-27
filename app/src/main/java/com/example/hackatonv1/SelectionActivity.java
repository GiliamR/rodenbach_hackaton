package com.example.hackatonv1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import static com.example.hackatonv1.Globals.setLang;

public class SelectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_selection);
        Button achtkm = (Button) findViewById(R.id.wandeling8km);
        achtkm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(SelectionActivity.this, GoogleMapsActivity.class));
            }
        });
        Button zeskm = (Button) findViewById(R.id.wandeling6km);
        zeskm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(SelectionActivity.this, GoogleMapsActivity.class));
            }
        });
    }
}