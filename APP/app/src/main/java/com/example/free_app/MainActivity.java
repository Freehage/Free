package com.example.free_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import android.widget.Toast;

import androidx.appcompat.widget.SearchView;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.example.free_app.after_search.MainSearchActivity;
import com.example.free_app.cardnews.CustomAdapter;
import com.example.free_app.database.DatabaseHelper;
import com.example.free_app.model.Product;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import me.relex.circleindicator.CircleIndicator3;

public class MainActivity extends AppCompatActivity {

    // camera
    private static final int REQUEST_IMAGE_CODE = 101;

    public static Context main_context;
    public int card_num = 0;

    private Button photo;
    private Button recycle;
    private ViewPager2 viewPager2;
    private FragmentStateAdapter fragmentStateAdapter;
    private int page = 4;
    private CircleIndicator3 circleIndicator3;
    private SearchView searchView;
    public List<Product> productlist;
    public int num = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        main_context = MainActivity.this;
        initLoadDB();

        openOrCreateDatabase("FreeAppDB.db", MODE_PRIVATE, null);
        // db.close() -- DO NOT USE THIS

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

        // 제품 촬영 버튼 클릭.
        photo = (Button) findViewById(R.id.photo);
        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_searchob = new Intent(getApplicationContext(), AfterDetectActivity.class);
                startActivity(intent_searchob);
                // takePicture();
            }
        });

        // 재활용 버튼 클릭.
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

    // camera 사진 찍기.
    public void takePicture() {
        Intent imageTakeIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if(imageTakeIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(imageTakeIntent, REQUEST_IMAGE_CODE);
        }
    }

}