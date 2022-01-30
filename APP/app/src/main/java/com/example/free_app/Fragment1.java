package com.example.free_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class Fragment1 extends Fragment implements View.OnClickListener {

    private ImageButton cardnews;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(
                R.layout.frame1,container,false);

        View view = inflater.inflate(R.layout.frame1,container,false);

        cardnews = (ImageButton) view.findViewById(R.id.cardnews);
        cardnews.setOnClickListener(this);


        return viewGroup;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.cardnews: {
                Toast.makeText(getContext(),"?",Toast.LENGTH_SHORT).show();
                Intent intent_cardnews = new Intent(getActivity(), CardnewsActivity.class);
                startActivity(intent_cardnews);
                break;
            }
        }

    }
}
