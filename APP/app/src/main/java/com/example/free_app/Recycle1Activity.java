package com.example.free_app;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.List;

public class Recycle1Activity extends AppCompatActivity {

    Button recycle_camera, paper, paper2, glass, pet, can, can2, vinyl, plastic;
    SearchView recycle_search_view;
    //TextView rest_name;

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

        List<User> mList = initLoadDatabase();

        initLoadDatabase();

        if (mList.get(0).getLevel() == 1){

            Toast.makeText(getApplicationContext(),"sssss",Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(getApplicationContext(),"xxxxxxxxxxxx",Toast.LENGTH_LONG).show();
        }


    }

    public List<User> initLoadDatabase() {

        DatabaseHelper databaseHelper = new DatabaseHelper(getApplicationContext());
        databaseHelper.OpenDatabaseFile();
        List<User> mList = databaseHelper.getTableData();
        mList = databaseHelper.getTableData();
        Log.e("test", String.valueOf(mList.size()));

        databaseHelper.close();
        return mList;
    }





}
