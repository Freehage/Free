package com.example.free_app.cardnews;

import android.os.Bundle;
import android.text.Html;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.free_app.MainActivity;
import com.example.free_app.R;


public class CardnewsActivity extends AppCompatActivity {
    private ImageView imageView;
    private LinearLayout layout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cardnews);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_space);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#000000'>카드뉴스 </font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        layout = (LinearLayout) findViewById(R.id.linear_card);

        imageView = findViewById(R.id.card_detail);
        int card_num = ((MainActivity)MainActivity.main_context).card_num;

        if(card_num == 1){
            ImageView img2 = new ImageView(CardnewsActivity.this);
            img2.setLayoutParams(new ViewGroup.LayoutParams(950, ViewGroup.LayoutParams.MATCH_PARENT));
            ImageView img3 = new ImageView(CardnewsActivity.this);
            img3.setLayoutParams(new ViewGroup.LayoutParams(950, ViewGroup.LayoutParams.MATCH_PARENT));
            ImageView img4 = new ImageView(CardnewsActivity.this);
            img4.setLayoutParams(new ViewGroup.LayoutParams(950, ViewGroup.LayoutParams.MATCH_PARENT));
            ImageView img5 = new ImageView(CardnewsActivity.this);
            img5.setLayoutParams(new ViewGroup.LayoutParams(950, ViewGroup.LayoutParams.MATCH_PARENT));
            ImageView img6 = new ImageView(CardnewsActivity.this);
            img6.setLayoutParams(new ViewGroup.LayoutParams(950, ViewGroup.LayoutParams.MATCH_PARENT));
            ImageView img7 = new ImageView(CardnewsActivity.this);
            img7.setLayoutParams(new ViewGroup.LayoutParams(950, ViewGroup.LayoutParams.MATCH_PARENT));
            layout.addView(img2);
            layout.addView(img3);
            layout.addView(img4);
            layout.addView(img5);
            layout.addView(img6);
            layout.addView(img7);
            Glide.with(CardnewsActivity.this).load("http://20.194.103.81/cardnews/zero.JPG").into(imageView);
            Glide.with(CardnewsActivity.this).load("http://20.194.103.81/cardnews/zero(1).JPG").into(img2);
            Glide.with(CardnewsActivity.this).load("http://20.194.103.81/cardnews/zero(2).JPG").into(img3);
            Glide.with(CardnewsActivity.this).load("http://20.194.103.81/cardnews/zero(3).JPG").into(img4);
            Glide.with(CardnewsActivity.this).load("http://20.194.103.81/cardnews/zero(4).JPG").into(img5);
            Glide.with(CardnewsActivity.this).load("http://20.194.103.81/cardnews/zero(5).JPG").into(img6);
            Glide.with(CardnewsActivity.this).load("http://20.194.103.81/cardnews/zero(6).JPG").into(img7);

        }
        else if(card_num == 2) {
            ImageView img2 = new ImageView(CardnewsActivity.this);
            img2.setLayoutParams(new ViewGroup.LayoutParams(950, ViewGroup.LayoutParams.MATCH_PARENT));
            ImageView img3 = new ImageView(CardnewsActivity.this);
            img3.setLayoutParams(new ViewGroup.LayoutParams(950, ViewGroup.LayoutParams.MATCH_PARENT));
            ImageView img4 = new ImageView(CardnewsActivity.this);
            img4.setLayoutParams(new ViewGroup.LayoutParams(950, ViewGroup.LayoutParams.MATCH_PARENT));
            ImageView img5 = new ImageView(CardnewsActivity.this);
            img5.setLayoutParams(new ViewGroup.LayoutParams(950, ViewGroup.LayoutParams.MATCH_PARENT));
            ImageView img6 = new ImageView(CardnewsActivity.this);
            img6.setLayoutParams(new ViewGroup.LayoutParams(950, ViewGroup.LayoutParams.MATCH_PARENT));
            layout.addView(img2);
            layout.addView(img3);
            layout.addView(img4);
            layout.addView(img5);
            layout.addView(img6);
            Glide.with(CardnewsActivity.this).load("http://20.194.103.81/cardnews/esg(1).JPG").into(imageView);
            Glide.with(CardnewsActivity.this).load("http://20.194.103.81/cardnews/esg(2).JPG").into(img2);
            Glide.with(CardnewsActivity.this).load("http://20.194.103.81/cardnews/esg(3).JPG").into(img3);
            Glide.with(CardnewsActivity.this).load("http://20.194.103.81/cardnews/esg(4).JPG").into(img4);
            Glide.with(CardnewsActivity.this).load("http://20.194.103.81/cardnews/esg(5).JPG").into(img5);
            Glide.with(CardnewsActivity.this).load("http://20.194.103.81/cardnews/esg(6).JPG").into(img6);
        }
        else if(card_num == 3) {
            ImageView img2 = new ImageView(CardnewsActivity.this);
            img2.setLayoutParams(new ViewGroup.LayoutParams(950, ViewGroup.LayoutParams.MATCH_PARENT));
            ImageView img3 = new ImageView(CardnewsActivity.this);
            img3.setLayoutParams(new ViewGroup.LayoutParams(950, ViewGroup.LayoutParams.MATCH_PARENT));
            ImageView img4 = new ImageView(CardnewsActivity.this);
            img4.setLayoutParams(new ViewGroup.LayoutParams(950, ViewGroup.LayoutParams.MATCH_PARENT));
            ImageView img5 = new ImageView(CardnewsActivity.this);
            img5.setLayoutParams(new ViewGroup.LayoutParams(950, ViewGroup.LayoutParams.MATCH_PARENT));
            ImageView img6 = new ImageView(CardnewsActivity.this);
            img6.setLayoutParams(new ViewGroup.LayoutParams(950, ViewGroup.LayoutParams.MATCH_PARENT));
            layout.addView(img2);
            layout.addView(img3);
            layout.addView(img4);
            layout.addView(img5);
            layout.addView(img6);
            Glide.with(CardnewsActivity.this).load("http://20.194.103.81/cardnews/social(6).png").into(imageView);
            Glide.with(CardnewsActivity.this).load("http://20.194.103.81/cardnews/social(1).png").into(img2);
            Glide.with(CardnewsActivity.this).load("http://20.194.103.81/cardnews/social(2).png").into(img3);
            Glide.with(CardnewsActivity.this).load("http://20.194.103.81/cardnews/social(3).png").into(img4);
            Glide.with(CardnewsActivity.this).load("http://20.194.103.81/cardnews/social(4).png").into(img5);
            Glide.with(CardnewsActivity.this).load("http://20.194.103.81/cardnews/social(5).png").into(img6);
        }
        else if(card_num == 4) {
            ImageView img2 = new ImageView(CardnewsActivity.this);
            img2.setLayoutParams(new ViewGroup.LayoutParams(950, ViewGroup.LayoutParams.MATCH_PARENT));
            ImageView img3 = new ImageView(CardnewsActivity.this);
            img3.setLayoutParams(new ViewGroup.LayoutParams(950, ViewGroup.LayoutParams.MATCH_PARENT));
            ImageView img4 = new ImageView(CardnewsActivity.this);
            img4.setLayoutParams(new ViewGroup.LayoutParams(950, ViewGroup.LayoutParams.MATCH_PARENT));
            ImageView img5 = new ImageView(CardnewsActivity.this);
            img5.setLayoutParams(new ViewGroup.LayoutParams(950, ViewGroup.LayoutParams.MATCH_PARENT));
            layout.addView(img2);
            layout.addView(img3);
            layout.addView(img4);
            layout.addView(img5);
            Glide.with(CardnewsActivity.this).load("http://20.194.103.81/cardnews/cup(1).png").into(imageView);
            Glide.with(CardnewsActivity.this).load("http://20.194.103.81/cardnews/cup(2).png").into(img2);
            Glide.with(CardnewsActivity.this).load("http://20.194.103.81/cardnews/cup(3).png").into(img3);
            Glide.with(CardnewsActivity.this).load("http://20.194.103.81/cardnews/cup(4).png").into(img4);
            Glide.with(CardnewsActivity.this).load("http://20.194.103.81/cardnews/cup(5).png").into(img5);
        }

    }
}
