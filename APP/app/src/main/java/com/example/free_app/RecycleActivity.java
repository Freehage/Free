package com.example.free_app;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;

import com.example.appkmpg.R;

public class RecycleActivity extends Activity{
    Button recycle_1, recycle_2, recycle_3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle);

        recycle_1 = findViewById(R.id.recycle_1);
        recycle_2 = findViewById(R.id.recycle_2);
        recycle_3 = findViewById(R.id.recycle_3);


    }
}



