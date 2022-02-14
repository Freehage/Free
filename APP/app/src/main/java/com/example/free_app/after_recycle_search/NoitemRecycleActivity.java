package com.example.free_app.after_recycle_search;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import com.example.free_app.MainActivity;
import com.example.free_app.R;
import com.example.free_app.after_search.MainSearchActivity;
import com.example.free_app.database.DatabaseHelper;

public class NoitemRecycleActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_space);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#000000'>검색결과 </font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_noitem);

        Intent intent = getIntent();
        String search_name = intent.getStringExtra("search_name");


        SearchView searchView = (SearchView) findViewById(R.id.noitem_search_bar);
        searchView.setQueryHint(search_name);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchView.setQueryHint("");
                Intent intent_search = new Intent(getApplicationContext(), RecycleSearchActivity.class);
                intent_search.putExtra("recycle_search_name",query);
                startActivity(intent_search);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchView.setQueryHint("");
                return true;
            }
        });

        TextView textView = findViewById(R.id.txt_noitem);
        textView.setText("아직 준비되지 않은 제품입니다.");
    }

}
