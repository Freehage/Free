package com.example.free_app.cardnews;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.free_app.MainActivity;
import com.example.free_app.R;

public class Fragment4 extends Fragment {
    private ImageButton cardnews4;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.frame4,container,false);

        cardnews4 = (ImageButton) viewGroup.findViewById(R.id.cardnews4);
        cardnews4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)MainActivity.main_context).card_num = 4;
                Intent intent_cardnews4 = new Intent(getActivity(), CardnewsActivity.class);
                startActivity(intent_cardnews4);
            }
        });


        return viewGroup;
    }
}
