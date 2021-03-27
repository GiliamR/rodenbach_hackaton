package com.example.hackatonv1;

import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.speech.tts.TextToSpeech;
import android.speech.tts.Voice;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

import static com.example.hackatonv1.Globals.getLang;
import static com.example.hackatonv1.Globals.getName;

public class InfoPage extends AppCompatActivity {
    TextToSpeech t1;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        CollapsingToolbarLayout toolBarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        TextView text = (TextView)findViewById(R.id.textView2);
        final ImageView foto = (ImageView) findViewById(R.id.imageView2);
        TextView nameBox = (TextView)findViewById(R.id.TextViewName);
        String name = getName();
        nameBox.setText(name);
        switch (name) {
            case "Mercator":
                foto.setImageResource(R.drawable.mercator);
                text.setText(R.string.text_mercator);
                break;

            case "Fiere Margriet":
                foto.setImageResource(R.drawable.fiere_margriet);
                text.setText(R.string.text_margriet);
                break;

            case "Rodenbach":
                foto.setImageResource(R.drawable.dirk_bouts);
                text.setText(R.string.text_lipsius);
                break;

            case "Erasmus":
                foto.setImageResource(R.drawable.erasmus);
                text.setText(R.string.text_erasmus);
                break;

            case "Vesalius":
                foto.setImageResource(R.drawable.andreas_vesalius);
                text.setText(R.string.text_vesalius);
                break;

            default:
                break;

        }
        Voice voiceobj = new Voice("it-it-x-kda#male_2-local",
                Locale.getDefault(), Voice.QUALITY_VERY_HIGH, 1, false, null);
        t1 = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    t1.setLanguage(Locale.forLanguageTag(Locale.getDefault().getLanguage()));
                    t1.setPitch(0.75f);
                    t1.setVoice(voiceobj);
                    t1.setSpeechRate(1f);
                }
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String toSpeak = text.getText().toString();
                if(t1.isSpeaking()) {
                    fab.setImageResource(android.R.drawable.ic_media_play);
                    t1.stop();
                } else {
                    t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
                    fab.setImageResource(android.R.drawable.ic_media_pause);
                }
            }
        });



    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to previous activity (if there is any)
        }

        return super.onOptionsItemSelected(item);

    }
    protected void onDestroy() {
        t1.shutdown();
        super.onDestroy();
    }
}