package com.example.hackatonv1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

public class ListOfInfoPages extends AppCompatActivity {

    ListView listView;
    String mTitle[] = {"statue1", "statue2", "statue3", "statue4", "statue5"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_info_pages);
    }
}