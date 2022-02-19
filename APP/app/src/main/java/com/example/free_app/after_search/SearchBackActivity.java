package com.example.free_app.after_search;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.free_app.MainActivity;
import com.example.free_app.R;

public class SearchBackActivity extends AppCompatActivity {

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
        String search_name = ((MainSearchActivity) MainSearchActivity.search_context).search_name;
        Intent intent = new Intent(getApplicationContext(),MainSearchActivity.class);
        intent.putExtra("search_name",search_name);
        startActivity(intent);
    }
}
