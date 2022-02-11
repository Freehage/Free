package com.example.free_app.Recycle_Method;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.free_app.R;

public class CanActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.can);

        getSupportActionBar().setTitle("캔 재활용 방법");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
}