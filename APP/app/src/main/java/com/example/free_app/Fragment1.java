package com.example.free_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;

public class Fragment1 extends Fragment {

    private ImageButton cardnews;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(
                R.layout.frame1,container,false);

        /*cardnews = (ImageButton) findViewById(R.id.cardnews);

        cardnews.setImageResource(R.drawable.cardnews);
        cardnews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_cardnews = new Intent(getApplicationContext(), CardnewsActivity.class);
                startActivity(intent_cardnews);
            }
        });*/

        return viewGroup;
    }
}
