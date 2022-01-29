package com.example.free_app;

import android.os.Bundle;
import android.widget.Button;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;

public class Recycle1Activity extends AppCompatActivity {

    Button recycle_camera, paper, paper2, glass, pet, can, can2, vinyl, plastic;
    SearchView recycle_search_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle1);

        getSupportActionBar().setTitle("재활용 방법 알아보기");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recycle_camera = findViewById(R.id.recycle_camera);

        paper = findViewById(R.id.paper);

        paper2 = findViewById(R.id.paper2);
        glass = findViewById(R.id.glass);
        pet = findViewById(R.id.pet);
        can = findViewById(R.id.can);
        can2 = findViewById(R.id.can2);
        vinyl = findViewById(R.id.vinyl);
        plastic = findViewById(R.id.plastic);



    }
}
