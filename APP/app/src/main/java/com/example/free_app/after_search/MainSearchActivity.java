package com.example.free_app.after_search;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import com.example.free_app.MainActivity;
import com.example.free_app.R;
import com.example.free_app.after_search.recommend.Carbon_Frag;
import com.example.free_app.after_search.recommend.Money_Frag;
import com.example.free_app.after_search.recommend.Recommend_Frag;
import com.example.free_app.after_search.recommend.Score_Frag;
import com.example.free_app.model.Product;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MainSearchActivity extends AppCompatActivity {
    public List<Product> productslists;
    private SearchView searchView;
    private List result_name;
    public static Context search_context;
    public String search_name;
    public Button button1;
    Fragment recommend_Frag, money_Frag, score_Frag, carbon_Frag;

    private Button btn_recommend,btn_money,btn_carbon,btn_score;

    //홈버튼 추가
    @Override
    public boolean onCreateOptionsMenu(Menu menu)    {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.home_button:
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aftersearch);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_space);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#000000'>검색결과 </font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        btn_recommend = findViewById(R.id.btn_recommend);
        btn_carbon = findViewById(R.id.btn_carbon);
        btn_money = findViewById(R.id.btn_money);
        btn_score = findViewById(R.id.btn_score);

        productslists = ((MainActivity)MainActivity.main_context).productlist;
        Intent intent = getIntent();
        search_name = intent.getStringExtra("search_name");
        btn_recommend.setBackgroundColor(Color.GRAY);

        recommend_Frag = new Recommend_Frag();
        money_Frag = new Money_Frag();
        carbon_Frag = new Carbon_Frag();
        score_Frag = new Score_Frag();

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

        Bundle bundle = new Bundle();
        bundle.putString("search_name",search_name);
        recommend_Frag.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_for_rec,recommend_Frag).commit();


        btn_recommend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)MainActivity.main_context).num = 0;
                btn_recommend.setBackgroundColor(Color.GRAY);
                btn_carbon.setBackgroundColor(Color.WHITE);
                btn_money.setBackgroundColor(Color.WHITE);
                btn_score.setBackgroundColor(Color.WHITE);
                Bundle bundle = new Bundle();
                bundle.putString("search_name",search_name);
                recommend_Frag.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_for_rec,recommend_Frag).commit();


            }
        });

        btn_carbon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)MainActivity.main_context).num = 1;
                btn_recommend.setBackgroundColor(Color.WHITE);
                btn_carbon.setBackgroundColor(Color.GRAY);
                btn_money.setBackgroundColor(Color.WHITE);
                btn_score.setBackgroundColor(Color.WHITE);
                Bundle bundle = new Bundle();
                bundle.putString("search_name",search_name);
                carbon_Frag.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_for_rec,carbon_Frag).commit();
            }
        });

        btn_money.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)MainActivity.main_context).num = 2;
                btn_recommend.setBackgroundColor(Color.WHITE);
                btn_carbon.setBackgroundColor(Color.WHITE);
                btn_money.setBackgroundColor(Color.GRAY);
                btn_score.setBackgroundColor(Color.WHITE);
                Bundle bundle = new Bundle();
                bundle.putString("search_name",search_name);
                money_Frag.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_for_rec,money_Frag).commit();
            }
        });

        btn_score.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)MainActivity.main_context).num = 3;
                btn_recommend.setBackgroundColor(Color.WHITE);
                btn_carbon.setBackgroundColor(Color.WHITE);
                btn_money.setBackgroundColor(Color.WHITE);
                btn_score.setBackgroundColor(Color.GRAY);
                Bundle bundle = new Bundle();
                bundle.putString("search_name",search_name);
                score_Frag.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_for_rec,score_Frag).commit();
            }
        });


    }
}
