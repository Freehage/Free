package com.example.free_app;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.appcompat.widget.SearchView;

import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.free_app.Recycle_Method.CanActivity;
import com.example.free_app.Recycle_Method.GlassActivity;
import com.example.free_app.Recycle_Method.Paper2Activity;
import com.example.free_app.Recycle_Method.PaperActivity;
import com.example.free_app.Recycle_Method.PlasticActivity;
import com.example.free_app.Recycle_Method.VinylActivity;
import com.example.free_app.after_recycle_search.RecycleSearchActivity;
import com.example.free_app.after_search.MainSearchActivity;
import com.example.free_app.database.DatabaseHelper;
import com.example.free_app.model.Product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Recycle1Activity extends AppCompatActivity {

    Button recycle_camera;
    Button paper, paper2, glass, can, vinyl, plastic;

    EditText et1;
    //Button bt1;
    TextView tv1;

    public ArrayList arrayList;

    private DatabaseHelper mDBHelper;

    //https://webisfree.com/2014-01-28/[mysql]-%ED%95%84%EB%93%9C%EC%97%90%EC%84%9C-%ED%8A%B9%EC%A0%95%EB%AC%B8%EC%9E%90-%ED%8F%AC%ED%95%A8-%EB%98%90%EB%8A%94-%EC%A0%9C%EC%99%B8%ED%95%9C-db-%EA%B2%80%EC%83%89-like-not
    //https://asterisco.tistory.com/70



    private SearchView recycle_search_view;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle1);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_space);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#000000'>재활용 방법 알아보기 </font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        //search
        recycle_search_view = (SearchView) findViewById(R.id.recycle_search_view);
        recycle_search_view.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                Intent intent_recyclesearch = new Intent(getApplicationContext(), RecycleSearchActivity.class);
                intent_recyclesearch.putExtra("recycle_search_name",s);
                startActivity(intent_recyclesearch);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        recycle_camera = findViewById(R.id.recycle_camera);



        paper = findViewById(R.id.paper);
        paper2 = findViewById(R.id.paper2);
        glass = findViewById(R.id.glass);
        can = findViewById(R.id.can);
        vinyl = findViewById(R.id.vinyl);
        plastic = findViewById(R.id.plastic);


        paper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_recycle = new Intent(getApplicationContext(), PaperActivity.class);
                startActivity(intent_recycle);
            }
        });


        paper2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_recycle = new Intent(getApplicationContext(), Paper2Activity.class);
                startActivity(intent_recycle);
            }
        });


        glass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_recycle = new Intent(getApplicationContext(), GlassActivity.class);
                startActivity(intent_recycle);
            }
        });



        can.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_recycle = new Intent(getApplicationContext(), CanActivity.class);
                startActivity(intent_recycle);
            }
        });



        vinyl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_recycle = new Intent(getApplicationContext(), VinylActivity.class);
                startActivity(intent_recycle);
            }
        });


        plastic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_recycle = new Intent(getApplicationContext(), PlasticActivity.class);
                startActivity(intent_recycle);
            }
        });



        //recycle_search_view = (SearchView) findViewById(R.id.recycle_search_view);


    }



}
