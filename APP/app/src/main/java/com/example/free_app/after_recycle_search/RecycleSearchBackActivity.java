package com.example.free_app.after_recycle_search;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


public class RecycleSearchBackActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String search_name = ((RecycleSearchActivity)RecycleSearchActivity.recycle_search_context).search_name;

        Intent intent = new Intent(getApplicationContext(), RecycleSearchActivity.class);
        intent.putExtra("recycle_search_name",search_name);
        startActivity(intent);
    }
}
