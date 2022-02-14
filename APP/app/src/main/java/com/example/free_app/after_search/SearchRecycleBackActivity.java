package com.example.free_app.after_search;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.free_app.MainActivity;
import com.example.free_app.R;

public class SearchRecycleBackActivity extends AppCompatActivity {

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
