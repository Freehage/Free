package com.example.free_app.Recycle_Method;

import android.os.Bundle;
import android.text.Html;

import androidx.appcompat.app.AppCompatActivity;

import com.example.free_app.R;

public class PlasticActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.plastic);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_space);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#000000'>플라스틱, 패트 재활용 방법 </font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
}