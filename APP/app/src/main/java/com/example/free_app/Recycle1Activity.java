package com.example.free_app;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.appcompat.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.free_app.Recycle_Method.CanActivity;
import com.example.free_app.Recycle_Method.GlassActivity;
import com.example.free_app.Recycle_Method.Paper2Activity;
import com.example.free_app.Recycle_Method.PaperActivity;
import com.example.free_app.Recycle_Method.PlasticActivity;
import com.example.free_app.Recycle_Method.VinylActivity;
import com.example.free_app.database.DatabaseHelper;
import com.example.free_app.model.Product;

import java.util.HashMap;
import java.util.List;

public class Recycle1Activity extends AppCompatActivity {

    Button recycle_camera, paper, paper2, glass, can, vinyl, plastic;

    private SearchView recycle_search_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle1);

        getSupportActionBar().setTitle("재활용 방법 알아보기");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recycle_camera = findViewById(R.id.recycle_camera);



        paper = findViewById(R.id.paper);
        paper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_recycle = new Intent(getApplicationContext(), PaperActivity.class);
                startActivity(intent_recycle);
            }
        });

        paper2 = findViewById(R.id.paper2);
        paper2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_recycle = new Intent(getApplicationContext(), Paper2Activity.class);
                startActivity(intent_recycle);
            }
        });

        glass = findViewById(R.id.glass);
        glass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_recycle = new Intent(getApplicationContext(), GlassActivity.class);
                startActivity(intent_recycle);
            }
        });


        can = findViewById(R.id.can);
        can.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_recycle = new Intent(getApplicationContext(), CanActivity.class);
                startActivity(intent_recycle);
            }
        });


        vinyl = findViewById(R.id.vinyl);
        vinyl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_recycle = new Intent(getApplicationContext(), VinylActivity.class);
                startActivity(intent_recycle);
            }
        });


        plastic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_recycle = new Intent(getApplicationContext(), PlasticActivity.class);
                startActivity(intent_recycle);
            }
        });



        recycle_search_view = (SearchView) findViewById(R.id.recycle_search_view);


    }




}
