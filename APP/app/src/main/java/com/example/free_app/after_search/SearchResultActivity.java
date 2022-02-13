package com.example.free_app.after_search;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.free_app.R;
import com.example.free_app.database.DatabaseHelper;
import com.example.free_app.model.Product;

import java.util.ArrayList;

public class SearchResultActivity  extends AppCompatActivity {

    private TextView txt_com;
    private TextView txt_obj;
    private TextView txt_level;
    private TextView txt_end;
    private RecommendAdapter adapter;
    private RecyclerView.LayoutManager mLayoutManager;

    //hs
    private DatabaseHelper mDBHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_space);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#000000'>상품 정보 알아보기</font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        String company = intent.getStringExtra("company");
        String object = intent.getStringExtra("object");
        String level = intent.getStringExtra("level");
        String end_date = intent.getStringExtra("end_date");

        txt_com = findViewById(R.id.txt_company);
        txt_obj = findViewById(R.id.txt_objname);
        txt_level = findViewById(R.id.txt_level);
        txt_end = findViewById(R.id.txt_enddate);

        txt_com.setText(company);
        txt_obj.setText(object);
        txt_level.setText("탄소 중립 LEVEL: " + level);
        txt_end.setText(end_date);

        //hs
        mDBHelper = new DatabaseHelper(this);
        String categoryResult = mDBHelper.getCategory(object);
        ArrayList arrayList_OB = new ArrayList();
        arrayList_OB = mDBHelper.getObjectsResult(categoryResult);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recommend);
        //recyclerView.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, includeEdge));
        //recyclerView.setLayoutManager(gridLayoutManager);
        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        adapter = new RecommendAdapter(arrayList_OB,this);
        recyclerView.setAdapter(adapter);

    }
}
