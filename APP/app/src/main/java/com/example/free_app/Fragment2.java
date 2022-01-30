package com.example.free_app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Fragment2 extends Fragment {
    private ImageButton cardnews2;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.frame2,container,false);

        cardnews2 = (ImageButton) viewGroup.findViewById(R.id.cardnews2);
        cardnews2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)MainActivity.main_context).card_num = 2;
                Intent intent_cardnews2 = new Intent(getActivity(), CardnewsActivity.class);
                startActivity(intent_cardnews2);
            }
        });


        return viewGroup;
    }
}
