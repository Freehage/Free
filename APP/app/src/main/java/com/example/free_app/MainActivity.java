package com.example.free_app;

import androidx.annotation.DrawableRes;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ScrollView;

import com.bumptech.glide.Glide;
import com.example.appkmpg.R;
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

        scrollView = (HorizontalScrollView) findViewById(R.id.scroll);
        cardnews = (ImageButton) findViewById(R.id.cardnews);
        cardnews.setImageResource(R.drawable.cardnews2);
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
        /*new Thread(new Runnable() {
            Bitmap resizebitmap = null;
            Bitmap bitmapCard = null;
            @Override
            public void run() {
                try{
                    bitmapCard = UrltoBitmap(cardnewsstring);
                    resizebitmap = Bitmap.createScaledBitmap(bitmapCard,950,950,true);
                }catch (Exception e){

                }finally {
                    if(resizebitmap != null ){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                cardnews.setImageBitmap(resizebitmap);
                            }
                        });
                    }
                }
            }
        }).start();*/

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
    private Bitmap UrltoBitmap(String url){
        URL imgurl = null;
        HttpURLConnection connection = null;
        InputStream inputStream = null;
        Bitmap bitmap = null;

        try{
            imgurl = new URL(url);
            connection = (HttpURLConnection) imgurl.openConnection();
            connection.setDoInput(true);
            connection.connect();
            inputStream = connection.getInputStream();
            bitmap = BitmapFactory.decodeStream(inputStream);
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
        finally {
            if(connection != null){
                connection.disconnect();
            }
            return bitmap;
        }
    }

}