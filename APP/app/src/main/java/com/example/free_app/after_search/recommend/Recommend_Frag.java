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
import java.util.Map;

public class Recommend_Frag extends Fragment {
    MainSearchActivity mainSearchActivity;
    private DatabaseHelper3 mDBHelper;
    private SearchAdapter adapter;

    double Carbon = ((MainActivity)MainActivity.main_context).Carbon;
    double Price = ((MainActivity)MainActivity.main_context).Price;
    double Score = ((MainActivity)MainActivity.main_context).Score;
    public float point = 0;

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
        ArrayList<String> item_list = userselectpoint(search_name,Carbon,Score,Price,getContext());


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
    public ArrayList<String> userselectpoint(String search, double carbon_point, double score_point, double price_point, Context context){
        DatabaseHelper3 mDBHelper = new DatabaseHelper3(context);
        ArrayList<Cursor> products = mDBHelper.getproductObject(search);
        Map<Float,String> return_value = null;
        ArrayList<String> real = null;
        Log.e("PRODUCE", String.valueOf(products.size()));

        try{
            for(int i = 0; i < products.size(); i++){
                products.get(i).moveToFirst();
                double carbon = products.get(i).getDouble(13);
                double score = products.get(i).getDouble(15);
                double price = products.get(i).getDouble(14);
                point = (float) (carbon_point * carbon + score_point * score + price_point * price);
                return_value.put(point,products.get(i).getString(1));
            }

            Object[] mapkey = return_value.keySet().toArray();
            Arrays.sort(mapkey);

        } catch (Exception e) {
            e.printStackTrace();
            for(Float nkey : return_value.keySet()){
                real.add(return_value.get(nkey));
            }
        }

        return real;
    }
}
