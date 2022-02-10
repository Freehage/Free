package com.example.free_app.cardnews;

import android.os.Bundle;
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

        getSupportActionBar().setTitle("카드뉴스");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        layout = (LinearLayout) findViewById(R.id.linear_card);

        imageView = findViewById(R.id.card_detail);
        int card_num = ((MainActivity)MainActivity.main_context).card_num;

        if(card_num == 1){
            ImageView img2 = new ImageView(CardnewsActivity.this);
            img2.setLayoutParams(new ViewGroup.LayoutParams(1100, ViewGroup.LayoutParams.MATCH_PARENT));
            ImageView img3 = new ImageView(CardnewsActivity.this);
            img3.setLayoutParams(new ViewGroup.LayoutParams(1100, ViewGroup.LayoutParams.MATCH_PARENT));
            ImageView img4 = new ImageView(CardnewsActivity.this);
            img4.setLayoutParams(new ViewGroup.LayoutParams(1100, ViewGroup.LayoutParams.MATCH_PARENT));
            ImageView img5 = new ImageView(CardnewsActivity.this);
            img5.setLayoutParams(new ViewGroup.LayoutParams(1100, ViewGroup.LayoutParams.MATCH_PARENT));
            ImageView img6 = new ImageView(CardnewsActivity.this);
            img6.setLayoutParams(new ViewGroup.LayoutParams(1100, ViewGroup.LayoutParams.MATCH_PARENT));
            ImageView img7 = new ImageView(CardnewsActivity.this);
            img7.setLayoutParams(new ViewGroup.LayoutParams(150, ViewGroup.LayoutParams.MATCH_PARENT));
            layout.addView(img2);
            layout.addView(img3);
            layout.addView(img4);
            layout.addView(img5);
            layout.addView(img6);
            layout.addView(img7);
            imageView.setImageResource(R.drawable.cardnews);
            Glide.with(CardnewsActivity.this).load("https://www.gihoo.or.kr/portal/upload/photo/20210901151752001.jpg").into(img2);
            Glide.with(CardnewsActivity.this).load("https://www.gihoo.or.kr/portal/upload/photo/20210901151825001.jpg").into(img3);
            Glide.with(CardnewsActivity.this).load("https://www.gihoo.or.kr/portal/upload/photo/20210901151859001.jpg").into(img4);
            Glide.with(CardnewsActivity.this).load("https://www.gihoo.or.kr/portal/upload/photo/20210901151948001.jpg").into(img5);
            Glide.with(CardnewsActivity.this).load("https://www.gihoo.or.kr/portal/upload/photo/20210901152011001.jpg").into(img6);

        }
        else if(card_num == 2) {
            ImageView img2 = new ImageView(CardnewsActivity.this);
            img2.setLayoutParams(new ViewGroup.LayoutParams(1100, ViewGroup.LayoutParams.MATCH_PARENT));
            ImageView img3 = new ImageView(CardnewsActivity.this);
            img3.setLayoutParams(new ViewGroup.LayoutParams(1100, ViewGroup.LayoutParams.MATCH_PARENT));
            ImageView img4 = new ImageView(CardnewsActivity.this);
            img4.setLayoutParams(new ViewGroup.LayoutParams(1100, ViewGroup.LayoutParams.MATCH_PARENT));
            ImageView img5 = new ImageView(CardnewsActivity.this);
            img5.setLayoutParams(new ViewGroup.LayoutParams(1100, ViewGroup.LayoutParams.MATCH_PARENT));
            ImageView img6 = new ImageView(CardnewsActivity.this);
            img6.setLayoutParams(new ViewGroup.LayoutParams(1100, ViewGroup.LayoutParams.MATCH_PARENT));
            ImageView img7 = new ImageView(CardnewsActivity.this);
            img7.setLayoutParams(new ViewGroup.LayoutParams(1100, ViewGroup.LayoutParams.MATCH_PARENT));
            ImageView img8 = new ImageView(CardnewsActivity.this);
            img8.setLayoutParams(new ViewGroup.LayoutParams(150, ViewGroup.LayoutParams.MATCH_PARENT));
            layout.addView(img2);
            layout.addView(img3);
            layout.addView(img4);
            layout.addView(img5);
            layout.addView(img6);
            layout.addView(img7);
            layout.addView(img8);
            imageView.setImageResource(R.drawable.cardnews2);
            Glide.with(CardnewsActivity.this).load("https://www.gihoo.or.kr/portal/upload/photo/20211203172510001.png").into(img2);
            Glide.with(CardnewsActivity.this).load("https://www.gihoo.or.kr/portal/upload/photo/20211203172519001.png").into(img3);
            Glide.with(CardnewsActivity.this).load("https://www.gihoo.or.kr/portal/upload/photo/20211203172616001.png").into(img4);
            Glide.with(CardnewsActivity.this).load("https://www.gihoo.or.kr/portal/upload/photo/20211203172625001.png").into(img5);
            Glide.with(CardnewsActivity.this).load("https://www.gihoo.or.kr/portal/upload/photo/20211203172654001.png").into(img6);
            Glide.with(CardnewsActivity.this).load("https://www.gihoo.or.kr/portal/upload/photo/20211203172803001.png").into(img7);
        }

    }
}
