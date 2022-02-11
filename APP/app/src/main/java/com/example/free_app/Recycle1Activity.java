package com.example.free_app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.free_app.database.DatabaseHelper;
import com.example.free_app.model.Product;

import java.util.HashMap;
import java.util.List;

public class Recycle1Activity extends AppCompatActivity {

    Button recycle_camera, paper, paper2, glass, pet, can, can2, vinyl, plastic;

    public TextView textView;
    SearchView recycle_search_view;;

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

        //rest_name = findViewById(R.id.rest_name);


        // 촬영 버튼을 클릭했을 경우.
        recycle_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RecycleCamera.class);
                startActivity(intent);
            }
        });



    }
}
