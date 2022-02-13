package com.example.free_app.after_search;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
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
import com.example.free_app.database.DatabaseHelper;
import com.example.free_app.model.Product;
import com.example.free_app.recycleTip.TipAdapter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MainSearchActivity extends AppCompatActivity {
    public List<Product> productslists;
    private SearchView searchView;
    private SearchAdapter adapter;
    private DatabaseHelper mDBHelper;
    private List result_name;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aftersearch);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_space);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#000000'>검색결과 </font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        productslists = ((MainActivity)MainActivity.main_context).productlist;

        Intent intent = getIntent();
        String search_name = intent.getStringExtra("search_name");

        mDBHelper = new DatabaseHelper(this);
        //출력값:: 이름들 list

        //ArrayList item_list = mDBHelper.getObjectResult(search_name);


        ArrayList<Product> item_list = search(search_name,productslists);



        searchView = (SearchView) findViewById(R.id.after_search_bar);
        searchView.setQueryHint(search_name);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchView.setQueryHint("");
                Intent intent_mainsearch = new Intent(getApplicationContext(), MainSearchActivity.class);
                intent_mainsearch.putExtra("search_name",query);
                startActivity(intent_mainsearch);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchView.setQueryHint("");
                return true;
            }
        });


        int spanCount = 2; // 3 columns
        int spacing = 100; // 50px
        boolean includeEdge = false;

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView_forsearch);
        //recyclerView.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, includeEdge));
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
