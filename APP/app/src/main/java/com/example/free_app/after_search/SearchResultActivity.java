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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.free_app.MainActivity;
import com.example.free_app.R;
import com.example.free_app.database.DatabaseHelper;
import com.example.free_app.model.Product;

import java.util.ArrayList;

public class SearchResultActivity  extends AppCompatActivity {

    private TextView txt_com;
    private TextView txt_obj;
    private TextView txt_level;
    private TextView txt_end;
    private TextView txt_rec;
    private RecommendAdapter adapter;
    private RecyclerView.LayoutManager mLayoutManager;
    public static Context context_search_recycle;
    public String company,objects,end_date,level,recycle_category;

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
        company = intent.getStringExtra("company");
        objects = intent.getStringExtra("object");
        level = intent.getStringExtra("level");
        end_date = intent.getStringExtra("end_date");
        recycle_category = intent.getStringExtra("recycle_category");

        txt_com = findViewById(R.id.txt_company);
        txt_obj = findViewById(R.id.txt_objname);
        txt_level = findViewById(R.id.txt_level);
        txt_end = findViewById(R.id.txt_enddate);
        txt_rec = findViewById(R.id.txt_recycle2);
        Button btn = findViewById(R.id.recycle);

        txt_com.setText(company);
        txt_obj.setText(objects);
        txt_level.setText("탄소 중립 LEVEL: " + level);
        txt_end.setText(end_date);
        txt_rec.setText(recycle_category);

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
        mDBHelper = new DatabaseHelper(this);
        String categoryResult = mDBHelper.getCategory(objects);
        ArrayList arrayList_OB = new ArrayList();
        arrayList_OB = mDBHelper.getObjectsResult(categoryResult);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recommend);
        //recyclerView.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, includeEdge));
        //recyclerView.setLayoutManager(gridLayoutManager);
        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        adapter = new RecommendAdapter(arrayList_OB,arrayList_back,this);
        recyclerView.setAdapter(adapter);

    }
}
