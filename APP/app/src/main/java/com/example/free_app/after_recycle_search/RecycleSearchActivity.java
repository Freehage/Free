package com.example.free_app.after_recycle_search;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.free_app.MainActivity;
import com.example.free_app.R;
import com.example.free_app.database.DatabaseHelper3;
import com.example.free_app.model.Product;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class RecycleSearchActivity extends AppCompatActivity {
    public List<Product> productslists;
    private SearchView searchView;
    private RecycleSearchAdapter adapter;
    private DatabaseHelper3 mDBHelper;
    private List result_name;
    public static Context recycle_search_context;
    public String search_name;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_afterrecyclesearch);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_space);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#000000'>검색결과 </font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        productslists = ((MainActivity)MainActivity.main_context).productlist;
        Log.e("PRODUCT", String.valueOf(productslists.size()));

        Intent intent = getIntent();
        search_name = intent.getStringExtra("recycle_search_name");

        mDBHelper = new DatabaseHelper3(this);

        ArrayList<Product> item_list = search(search_name,productslists);


        searchView = (SearchView) findViewById(R.id.after_recycle_search_bar);
        searchView.setQueryHint(search_name);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchView.setQueryHint("");
                Intent intent_recyclesearch = new Intent(getApplicationContext(), RecycleSearchActivity.class);
                intent_recyclesearch.putExtra("recycle_search_name",query);
                startActivity(intent_recyclesearch);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchView.setQueryHint("");
                return true;
            }
        });

        if(item_list.size() != 0){
            RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView_forrecyclesearch);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2);
            recyclerView.setLayoutManager(gridLayoutManager);
            adapter = new RecycleSearchAdapter(item_list,this);
            recyclerView.setAdapter(adapter);
        }else{
            Intent intent1 = new Intent(getApplicationContext(), NoitemRecycleActivity.class);
            intent1.putExtra("search_name",search_name);
            startActivity(intent1);
        }



    }

    private ArrayList<Product> search(String search_name, List<Product> productslist) {
        ArrayList<Product> return_value = new ArrayList<Product>();
        Iterator<Product> iterator = productslist.iterator();
        while(iterator.hasNext()) {
            Product item = iterator.next();
            String searchs = item.getObject();
            try{
                if (searchs.contains(search_name)) {
                    return_value.add(item);
                }
            } catch (Exception e) {
                e.printStackTrace();
                return return_value;
            }
        }
        return return_value;
    }
}

