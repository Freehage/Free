package com.example.free_app.cardnews;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class CustomAdapter extends FragmentStateAdapter {
    public int C;

    public CustomAdapter(FragmentActivity fragmentActivity, int count){
        super(fragmentActivity);
        C = count;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        int index = getRealPosition(position);

        if(index==0){
            return new Fragment1();
        }
        else{
            return new Fragment2();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }

    public int getRealPosition(int position) {
        return position % C;
    }
}
