package com.example.free_app;

import androidx.annotation.DrawableRes;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ScrollView;

import com.bumptech.glide.Glide;
import com.example.free_app.CardnewsActivity;
import com.example.free_app.RecycleActivity;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import me.relex.circleindicator.CircleIndicator3;

public class MainActivity extends AppCompatActivity {

    public static Context main_context;
    public int card_num = 0;

    private Button recycle;
    private ViewPager2 viewPager2;
    private FragmentStateAdapter fragmentStateAdapter;
    private int page = 2;
    private CircleIndicator3 circleIndicator3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        main_context = MainActivity.this;

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
        viewPager2.setOffscreenPageLimit(2);
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





        recycle = (Button) findViewById(R.id.recycle);
        recycle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_recycle = new Intent(getApplicationContext(), RecycleActivity.class);
                startActivity(intent_recycle);

            }
        });

    }

}