package com.example.hackatonv1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class PopupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_popup);
        ImageButton buttonCancel = (ImageButton) findViewById(R.id.cancelButton);
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(PopupActivity.this, GoogleMapsActivity.class));

            }
        });

        Button btnContinue = (Button) findViewById(R.id.seeMoreBtn);
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(PopupActivity.this, InfoPage.class));

            }
        });
    }
}