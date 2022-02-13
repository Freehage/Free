package com.example.free_app.after_search;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SearchRecycleBackActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String company = ((SearchResultActivity)SearchResultActivity.context_search_recycle).company;
        String object = ((SearchResultActivity)SearchResultActivity.context_search_recycle).objects;
        String level = ((SearchResultActivity)SearchResultActivity.context_search_recycle).level;
        String end_date = ((SearchResultActivity)SearchResultActivity.context_search_recycle).end_date;
        String rec = ((SearchResultActivity)SearchResultActivity.context_search_recycle).recycle_category;

        Intent intent = new Intent(getApplicationContext(), SearchResultActivity.class);
        intent.putExtra("company",company);
        intent.putExtra("object",object);
        intent.putExtra("level",level);
        intent.putExtra("end_date",end_date);
        intent.putExtra("recycle_category",rec);
        startActivity(intent);

    }
}
