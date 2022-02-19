package com.example.free_app.after_search;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.free_app.MainActivity;
import com.example.free_app.R;
import com.example.free_app.database.DatabaseHelper3;

import java.util.ArrayList;

public class SearchResultActivity  extends AppCompatActivity {

    private TextView txt_com;
    private TextView txt_obj;
    private TextView txt_level;
    private TextView txt_end;
    private TextView txt_rec,txt;
    private RecommendAdapter adapter;
    private RecyclerView.LayoutManager mLayoutManager;
    public static Context context_search_recycle;
    public String company,objects,end_date,level,recycle_category,amount,url_gonghome;
    private DatabaseHelper3 mDBHelper;

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
        setContentView(R.layout.activity_result);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_space);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#000000'>상품 정보 알아보기</font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        company = intent.getStringExtra("company");
        objects = intent.getStringExtra("object");
        level = intent.getStringExtra("level");
        end_date = intent.getStringExtra("end_date");
        amount = intent.getStringExtra("carbon_amount");
        recycle_category = intent.getStringExtra("recycle_category");
        url_gonghome = intent.getStringExtra("url");

        txt_com = findViewById(R.id.txt_company);
        txt_obj = findViewById(R.id.txt_objname);
        txt_level = findViewById(R.id.txt_level);
        txt_end = findViewById(R.id.txt_enddate);
        txt_rec = findViewById(R.id.txt_recycle2);
        Button btn = findViewById(R.id.recycle);
        Button gonghome = findViewById(R.id.buy);
        txt = findViewById(R.id.txt);

        txt_com.setText(company);
        txt_obj.setText(objects);
        if(level.contains("0")){
            txt_level.setText("해당 제품은 저탄소 제품 인증 마크가 없는 제품입니다");
            txt.setText("RE⧖BON이 탄소 중립 제품을 추천합니다");
            txt.setTextColor(Color.BLACK);
            txt_end.setText("재활용 분류: "+recycle_category);
        }else{
            txt_end.setText(end_date +"원");
            txt_rec.setText("재활용 분류: "+ recycle_category);
        }


        gonghome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url_gonghome));
                startActivity(intent);
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SearchRecycle.class);
                intent.putExtra("recycle_category",recycle_category);
                startActivity(intent);
            }
        });

        ArrayList<String> arrayList_back = new ArrayList();
        arrayList_back.add(company);
        arrayList_back.add(objects);
        arrayList_back.add(level);
        arrayList_back.add(end_date);

        //hs
        mDBHelper = new DatabaseHelper3(this);
        String categoryResult = mDBHelper.getCategory(objects);
        ArrayList arrayList_OB = new ArrayList();
        arrayList_OB = mDBHelper.getObjectsResult(categoryResult);


        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recommend);
        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        adapter = new RecommendAdapter(arrayList_OB,arrayList_back,this);
        recyclerView.setAdapter(adapter);

    }
}
