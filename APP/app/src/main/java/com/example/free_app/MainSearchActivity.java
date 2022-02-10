package com.example.free_app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.free_app.model.Product;

import java.util.Iterator;
import java.util.List;

public class MainSearchActivity extends AppCompatActivity {
    private TextView search_obj, search_case, search_company;
    public List<Product> productslists;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_aftersearch);

        getSupportActionBar().setTitle("검색 결과");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        productslists = ((MainActivity)MainActivity.main_context).productlist;

        Intent intent = getIntent();

        String search_name = intent.getStringExtra("search_name");

        search_case = (TextView) findViewById(R.id.search_case);
        search_obj = (TextView) findViewById(R.id.search_obj);
        search_company = (TextView) findViewById(R.id.search_company);

        Product item = search(search_name,productslists);

        if(item != null){
            Log.e("HERE","have search name");
            search_case.setText(item.getCompany());
            search_obj.setText(item.getObject());
            search_company.setText(item.getCompany());
        }
        else{
            Log.e("HERE","no "+search_name);
            search_obj.setText("아직 제품이 준비되지 않았습니다.");
        }



    }

    private Product search(String search_name, List<Product> productslists) {
        Iterator<Product> iterator = productslists.iterator();
        while (iterator.hasNext()) {
            Product item = iterator.next();
            String searchs = item.getObject();
            if (searchs.equals(search_name)) {
                return item;
            }
        }
        return null;
    }
}
