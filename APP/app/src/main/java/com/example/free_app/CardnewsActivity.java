package com.example.free_app;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


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
            Toast.makeText(this,"?",Toast.LENGTH_LONG).show();
            ImageView img2 = new ImageView(CardnewsActivity.this);
            img2.setLayoutParams(new ViewGroup.LayoutParams(1100,1100));
            ImageView img3 = new ImageView(CardnewsActivity.this);
            img3.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

            layout.addView(img2);
            layout.addView(img3);
            imageView.setImageResource(R.drawable.cardnews);
            img2.setImageResource(R.drawable.cardnews);
            img3.setImageResource(R.drawable.cardnews2);

        }

    }
}
