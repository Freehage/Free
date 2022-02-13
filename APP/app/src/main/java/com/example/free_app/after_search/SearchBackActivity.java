package com.example.free_app.after_search;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.free_app.R;

public class SearchBackActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_aftersearch);

        String search_name = ((MainSearchActivity) MainSearchActivity.search_context).search_name;
        Intent intent = new Intent(getApplicationContext(),MainSearchActivity.class);
        intent.putExtra("search_name",search_name);
        startActivity(intent);
    }
}
