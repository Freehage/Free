package com.example.free_app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.free_app.R;

public class RecycleActivity extends AppCompatActivity {
    Button recycle_1, recycle_2, recycle_3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle);

        getSupportActionBar().setTitle("재활용 실천하기");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recycle_1 = findViewById(R.id.recycle_1);
        recycle_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),Recycle1Activity.class);
                startActivity(intent);

            }
        });

        recycle_2 = findViewById(R.id.recycle_2);

        recycle_3 = findViewById(R.id.recycle_3);
        recycle_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),Recycle3Activity.class);
                startActivity(intent);

            }
        });

    }
}



