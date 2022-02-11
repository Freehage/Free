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

import com.example.free_app.database.DatabaseHelper;
import com.example.free_app.model.Product;

import java.util.HashMap;
import java.util.List;

public class Recycle1Activity extends AppCompatActivity {

    Button recycle_camera, paper, paper2, glass, pet, can, can2, vinyl, plastic;

    private SearchView recycle_search_view;

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


        recycle_search_view = (SearchView) findViewById(R.id.recycle_search_view);
        recycle_search_view.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                Intent intent_recycle_search = new Intent(getApplicationContext(),RecycleSearchActivity.class);
                intent_recycle_search.putExtra("re_search_name",s);
                startActivity(intent_recycle_search);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });



    }




}
