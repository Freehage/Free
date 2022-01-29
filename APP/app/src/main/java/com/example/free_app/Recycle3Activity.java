package com.example.free_app;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class Recycle3Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle3);

        getSupportActionBar().setTitle("제로웨이스트샵 찾아보기");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


}
