package com.example.free_app.after_search.recommend;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.free_app.MainActivity;
import com.example.free_app.R;
import com.example.free_app.after_search.MainSearchActivity;
import com.example.free_app.after_search.NoitemActivity;
import com.example.free_app.after_search.SearchAdapter;
import com.example.free_app.database.DatabaseHelper3;
import com.example.free_app.model.Product;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Recommend_Frag extends Fragment {
    MainSearchActivity mainSearchActivity;
    private DatabaseHelper3 mDBHelper;
    private SearchAdapter adapter;

    double Carbon = ((MainActivity)MainActivity.main_context).Carbon;
    double Price = ((MainActivity)MainActivity.main_context).Price;
    double Score = ((MainActivity)MainActivity.main_context).Score;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mainSearchActivity = (MainSearchActivity)getActivity();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mainSearchActivity = null;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.activity_aftersearchframe,container,false);

        String search_name = getArguments().getString("search_name");
        Log.e("RECOMMEND","!");

        mDBHelper = new DatabaseHelper3(getActivity().getApplicationContext());
        ArrayList<String> item_list = mDBHelper.getproductObject(search_name,Carbon,Score,Price,getContext());

        if(item_list.size() != 0){
            RecyclerView recyclerView = (RecyclerView) container.findViewById(R.id.recyclerView_forsearch);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity().getApplicationContext(),2);
            recyclerView.setLayoutManager(gridLayoutManager);
            adapter = new SearchAdapter(item_list,getActivity().getApplicationContext());
            recyclerView.setAdapter(adapter);
        }else{
            Intent intent1 = new Intent(getActivity().getApplicationContext(), NoitemActivity.class);
            intent1.putExtra("search_name",search_name);
            startActivity(intent1);
        }

        return viewGroup;
    }

}
