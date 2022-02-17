package com.example.free_app.after_search.recommend;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.free_app.R;
import com.example.free_app.after_search.MainSearchActivity;
import com.example.free_app.after_search.NoitemActivity;
import com.example.free_app.after_search.SearchAdapter;
import com.example.free_app.database.DatabaseHelper;

import java.util.ArrayList;

public class Carbon_Frag extends Fragment {

    MainSearchActivity mainSearchActivity;
    private DatabaseHelper mDBHelper;
    private SearchAdapter adapter;

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

        mDBHelper = new DatabaseHelper(getActivity().getApplicationContext());
        ArrayList<String> item_list = mDBHelper.getObjectsResult_for_recommend(search_name);


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
