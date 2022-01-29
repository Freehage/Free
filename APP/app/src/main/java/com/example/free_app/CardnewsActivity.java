package com.example.free_app;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;


public class CardnewsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cardnews);

        getSupportActionBar().setTitle("카드뉴스");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }
}
