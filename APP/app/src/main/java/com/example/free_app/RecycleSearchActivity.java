package com.example.free_app;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.free_app.model.Product;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class RecycleSearchActivity extends AppCompatActivity {
    public List<Product> recycle_product;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_recycle1_search);

        getSupportActionBar().setTitle("검색 결과");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recycle_product = ((MainActivity)MainActivity.main_context).productlist;

        Intent intent = getIntent();

        String search_name = intent.getStringExtra("re_search_name");

        TextView search_case = (TextView) findViewById(R.id.search_case);
        TextView search_obj = (TextView) findViewById(R.id.search_obj);
        TextView search_company = (TextView) findViewById(R.id.search_company);
        TextView a = (TextView)findViewById(R.id.a);

        Product item = search(search_name,recycle_product);

        if(item != null){
            Log.e("HERE","have search name");
            //a.setText(item.getObj());
            search_case.setText(item.getCompany());
            search_obj.setText(item.getObject());
            search_company.setText(item.getCompany());
        }
        else{
            Log.e("HERE","no "+search_name);
            search_obj.setText("아직 제품이 준비되지 않았습니다.");
        }



    }

    private Product search(String search_name, List<Product> recycle_product) {
        for (int i =0; i < recycle_product.size(); i++){
            String item = recycle_product.get(i).getObject();
            if(item.contains(search_name)){
                return recycle_product.get(i);
            }
        }
        return null;

    }


    /*private Product search(String search_name, List<Product> recycle_product) {
        Iterator<Product> iterator = recycle_product.iterator();
        while (iterator.hasNext()) {
            Product item = iterator.next();
            String searchs = item.getObject();
            if (Objects.equals(searchs,search_name)) {
                return item;
            }
        }
        return null;
    }*/


}
