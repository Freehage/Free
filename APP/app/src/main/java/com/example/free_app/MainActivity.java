package com.example.free_app;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.widget.SearchView;

import com.example.free_app.after_search.MainSearchActivity;
import com.example.free_app.cardnews.CustomAdapter;
import com.example.free_app.database.DatabaseHelper;
import com.example.free_app.model.Product;

import java.util.List;

import me.relex.circleindicator.CircleIndicator3;

public class MainActivity extends AppCompatActivity {

    public static Context main_context;
    public int card_num = 0;

    private Button recycle;
    private ViewPager2 viewPager2;
    private FragmentStateAdapter fragmentStateAdapter;
    private int page = 4;
    private CircleIndicator3 circleIndicator3;
    private SearchView searchView;
    public List<Product> productlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        main_context = MainActivity.this;
        initLoadDB();

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        viewPager2 = findViewById(R.id.viewpager);
        fragmentStateAdapter = new CustomAdapter(MainActivity.this, page);
        viewPager2.setAdapter(fragmentStateAdapter);
        circleIndicator3 = findViewById(R.id.indicator);
        circleIndicator3.setViewPager(viewPager2);
        circleIndicator3.createIndicators(page,0);
        viewPager2.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);

        viewPager2.setCurrentItem(0);
        viewPager2.setOffscreenPageLimit(4);
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                if(positionOffsetPixels == 0){
                    viewPager2.setCurrentItem(position);
                }
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                circleIndicator3.animatePageSelected(position%page);
            }
        });

        //search
        searchView = (SearchView) findViewById(R.id.search_bar);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                Intent intent_mainsearch = new Intent(getApplicationContext(), MainSearchActivity.class);
                intent_mainsearch.putExtra("search_name",s);
                startActivity(intent_mainsearch);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });





        recycle = (Button) findViewById(R.id.recycle);
        recycle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_recycle = new Intent(getApplicationContext(), RecycleActivity.class);
                startActivity(intent_recycle);

            }
        });

    }

    private void initLoadDB(){

        DatabaseHelper databaseHelper = new DatabaseHelper(getApplicationContext());
        databaseHelper.openDB();

        productlist = databaseHelper.getTableData();
        Log.e("TEST",String.valueOf(productlist.size()));
        databaseHelper.close();

    }


}