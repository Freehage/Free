package com.example.free_app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class Fragment1 extends Fragment {

    private ImageButton cardnews;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.frame1,container,false);

        cardnews = (ImageButton) viewGroup.findViewById(R.id.cardnews);
        cardnews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)MainActivity.main_context).card_num = 1;
                Intent intent_cardnews = new Intent(getActivity(), CardnewsActivity.class);
                startActivity(intent_cardnews);
            }
        });

        return viewGroup;
    }

}
