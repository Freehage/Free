package com.example.free_app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.free_app.model.Product;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MainSearchActivity extends AppCompatActivity {
    public List<Product> productslists;
    private LinearLayout layoutforsearch;
    private RelativeLayout relativeforsearch;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_aftersearch);
        layoutforsearch = (LinearLayout) findViewById(R.id.linear_result1);
        relativeforsearch = (RelativeLayout) findViewById(R.id.relativeaftersearch);

        getSupportActionBar().setTitle("검색 결과");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        productslists = ((MainActivity)MainActivity.main_context).productlist;

        Intent intent = getIntent();

        String search_name = intent.getStringExtra("search_name");

        ImageView imageView = (ImageView) findViewById(R.id.search_img);
        TextView search_case = (TextView) findViewById(R.id.search_case);
        TextView search_obj = (TextView) findViewById(R.id.search_obj);
        TextView search_company = (TextView) findViewById(R.id.search_company);

        List<Product> item_list = search(search_name,productslists);

        if(item_list != null){
            Log.e("HERE","have search name");
            for(Product product: item_list){
                Log.e("HERE",Integer.toString(item_list.size()));
                search_case.setText(product.getOboutC());
                search_obj.setText(product.getObject());
                search_company.setText(product.getCompany());
            }

        }
        else{
            Log.e("HERE","no "+search_name);
            search_obj.setText("아직 제품이 준비되지 않았습니다.");
        }



    }

    private List<Product> search(String search_name, List<Product> productslists) {
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
