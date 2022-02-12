package com.example.free_app.after_search;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.free_app.MainActivity;
import com.example.free_app.R;
import com.example.free_app.model.Product;
import com.example.free_app.recycleTip.TipAdapter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MainSearchActivity extends AppCompatActivity {
    public List<Product> productslists;
    private SearchView searchView;
    private SearchAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aftersearch);
        getSupportActionBar().setTitle("검색 결과");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);

        productslists = ((MainActivity)MainActivity.main_context).productlist;

        Intent intent = getIntent();
        String search_name = intent.getStringExtra("search_name");
        ArrayList<Product> item_list = search(search_name,productslists);

        searchView = (SearchView) findViewById(R.id.after_search_bar);
        searchView.setQueryHint(search_name);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView_forsearch);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(gridLayoutManager);
        adapter = new SearchAdapter(item_list,this);
        recyclerView.setAdapter(adapter);



    }

    private ArrayList<Product> search(String search_name, List<Product> productslists) {
        ArrayList<Product> return_value = new ArrayList<Product>();
        Iterator<Product> iterator = productslists.iterator();
        while (iterator.hasNext()) {
            Product item = iterator.next();
            String searchs = item.getObject();
            if (searchs.contains(search_name)) {
                return_value.add(item);
            }
        }
        return return_value;
    }
}
