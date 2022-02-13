package com.example.free_app.after_search;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.free_app.R;

import java.util.ArrayList;

public class ClickRecommend extends AppCompatActivity {
    public String recycle_category;
    public static Context recommend_context;
    public ArrayList<String> arrayList;
    public String company,objects,end_date,level;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_recommend);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_space);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#000000'>상품 정보 알아보기 </font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView txt_com = findViewById(R.id.txt_company);
        TextView txt_obj = findViewById(R.id.txt_objname);
        TextView txt_level = findViewById(R.id.txt_level);
        TextView txt_end = findViewById(R.id.txt_enddate);
        TextView txt_rec = findViewById(R.id.txt_recycle2);
        Button recycle_btn = findViewById(R.id.recycle_btn);

        Intent intent = getIntent();
        arrayList = intent.getStringArrayListExtra("backlist");

        if(intent != null){
            company = intent.getStringExtra("company");
            txt_com.setText(company);
            end_date = intent.getStringExtra("end_date");
            txt_end.setText(end_date);
            level = intent.getStringExtra("level");
            txt_level.setText("탄소 중립 LEVEL: " + level);
            objects = intent.getStringExtra("object");
            txt_obj.setText(objects);
            recycle_category = intent.getStringExtra("recycle_category");
            txt_rec.setText(recycle_category);
        }

        recycle_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RecommendRecycle.class);
                intent.putExtra("recycle_category",recycle_category);
                startActivity(intent);
            }
        });

    }
}
