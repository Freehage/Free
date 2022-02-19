package com.example.free_app.after_search;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.free_app.MainActivity;
import com.example.free_app.R;

import java.util.ArrayList;

public class ClickRecommend extends AppCompatActivity {
    public String recycle_category;
    public static Context recommend_context;
    public ArrayList<String> arrayList;
    public String company,objects,end_date,level,url_gonghome;

    //홈버튼 추가
    @Override
    public boolean onCreateOptionsMenu(Menu menu)    {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.home_button:
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_recommend);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_space);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#000000'>상품 정보 알아보기 </font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView txt_com = findViewById(R.id.txt_company2);
        TextView txt_obj = findViewById(R.id.txt_objname2);
        TextView txt_level = findViewById(R.id.txt_level2);
        TextView txt_end = findViewById(R.id.txt_enddate2);
        TextView txt_rec = findViewById(R.id.txt_recycle2);
        Button recycle_btn = findViewById(R.id.recycle_btn);
        Button gonghomeurl = findViewById(R.id.buy);

        Intent intent = getIntent();
        arrayList = intent.getStringArrayListExtra("backlist");

        if(intent != null){
            company = intent.getStringExtra("company");
            txt_com.setText(company);
            end_date = intent.getStringExtra("carbon_amount");
            txt_end.setText("탄소 배출량: "+ end_date);
            level = intent.getStringExtra("level");
            txt_level.setText("탄소 중립 LEVEL: " + level);
            objects = intent.getStringExtra("object");
            txt_obj.setText(objects);
            recycle_category = intent.getStringExtra("recycle_category");
            txt_rec.setText("재활용 분류: " + recycle_category);
            url_gonghome = intent.getStringExtra("url");
        }

        gonghomeurl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url_gonghome));
                startActivity(intent);
            }
        });

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
