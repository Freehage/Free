package com.example.free_app;

import androidx.annotation.DrawableRes;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

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

public class MainActivity extends AppCompatActivity {

    private Button recycle;
    private ImageButton cardnews;
    HorizontalScrollView scrollView;
    boolean scroll_flag = true;
    public Integer cardnews_num = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        scrollView = (HorizontalScrollView) findViewById(R.id.scroll);
        cardnews = (ImageButton) findViewById(R.id.cardnews);

        cardnews.setImageResource(R.drawable.cardnews);
        scrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View view, int i, int i1, int i2, int i3) {
                if(scroll_flag){
                    if((!view.canScrollHorizontally(1))){
                        cardnews.setImageResource(R.drawable.cardnews2);
                        cardnews_num = 2;
                    }
                    else if((!view.canScrollHorizontally(-1))){
                        cardnews.setImageResource(R.drawable.cardnews);
                        cardnews_num = 1;
                    }
                }
            }
        });

        cardnews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_cardnews = new Intent(getApplicationContext(), CardnewsActivity.class);
                startActivity(intent_cardnews);
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