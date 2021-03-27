package com.example.hackatonv1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import static com.example.hackatonv1.Globals.getId;
import static com.example.hackatonv1.Globals.getLang;
import static com.example.hackatonv1.Globals.getName;
import static com.example.hackatonv1.Globals.setId;
import static com.example.hackatonv1.Globals.setName;

public class PopupActivity extends AppCompatActivity {
    String name ;
    String pictureId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        name = getName();
        pictureId = getId();

        setContentView(R.layout.activity_popup);
        final TextView titel = (TextView) findViewById(R.id.PopUpName);
        final ImageView foto = (ImageView) findViewById(R.id.popupFoto);
        titel.setText(name);
        switch (name) {
            case "Mercator":
                foto.setImageResource(R.drawable.mercator);
                break;

            case "Fiere Margriet":
                foto.setImageResource(R.drawable.fiere_margriet);
                break;

            case "Rodenbach":
                foto.setImageResource(R.drawable.dirk_bouts);
                break;

            case "Erasmus":
                foto.setImageResource(R.drawable.erasmus);
                break;

            case "Vesalius":
                foto.setImageResource(R.drawable.andreas_vesalius);
                break;

            default:
                break;

        }


        ImageButton buttonCancel = (ImageButton) findViewById(R.id.cancelButton);
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(PopupActivity.this, GoogleMapsActivity.class));

            }});

        Button btnContinue = (Button) findViewById(R.id.seeMoreBtn);
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(PopupActivity.this, InfoPage.class));

            }
        });
    }
}