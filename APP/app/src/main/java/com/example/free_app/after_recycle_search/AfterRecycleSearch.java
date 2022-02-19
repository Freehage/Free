package com.example.free_app.after_recycle_search;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.free_app.R;
import com.example.free_app.Recycle1Activity;


public class AfterRecycleSearch extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_space);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#000000'>재활용 방법 </font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        String recycle_category = intent.getStringExtra("recycle_category");

        if (recycle_category.equals("종이")){
            setContentView(R.layout.paper);
        }else if(recycle_category.equals("종이팩")){
            setContentView(R.layout.paper2);
        }else if(recycle_category.equals("유리")){
            setContentView(R.layout.glass);
        }else if(recycle_category.equals("알루미늄")){
            setContentView(R.layout.can);
        }else if(recycle_category.equals("비닐")){
            setContentView(R.layout.vinyl);
        }else if(recycle_category.equals("플라스틱") || recycle_category.equals("페트") ){
            setContentView(R.layout.plastic);
        }else{
            Intent intents = new Intent(getApplicationContext(), Recycle1Activity.class);
            startActivity(intents);
        }
    }
}
