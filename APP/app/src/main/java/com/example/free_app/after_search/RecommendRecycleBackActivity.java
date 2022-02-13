package com.example.free_app.after_search;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class RecommendRecycleBackActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String company = ((ClickRecommend)ClickRecommend.recommend_context).company;
        String object = ((ClickRecommend)ClickRecommend.recommend_context).objects;
        String level = ((ClickRecommend)ClickRecommend.recommend_context).level;
        String end_date = ((ClickRecommend)ClickRecommend.recommend_context).end_date;

        Intent intent = new Intent(getApplicationContext(), SearchResultActivity.class);
        intent.putExtra("company",company);
        intent.putExtra("object",object);
        intent.putExtra("level",level);
        intent.putExtra("end_date",end_date);
        startActivity(intent);

    }
}
