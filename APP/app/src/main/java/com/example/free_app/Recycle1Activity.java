package com.example.free_app;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.free_app.Recycle_Method.CanActivity;
import com.example.free_app.Recycle_Method.GlassActivity;
import com.example.free_app.Recycle_Method.Paper2Activity;
import com.example.free_app.Recycle_Method.PaperActivity;
import com.example.free_app.Recycle_Method.PlasticActivity;
import com.example.free_app.Recycle_Method.VinylActivity;
import com.example.free_app.after_recycle_search.RecycleSearchActivity;

import java.util.ArrayList;

public class Recycle1Activity extends AppCompatActivity {

    Button recycle_camera;
    Button paper, paper2, glass, can, vinyl, plastic;

    public ArrayList arrayList;
    private SearchView recycle_search_view;

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
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


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

        // 촬영 버튼을 클릭했을 경우.
        recycle_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RecycleCamera.class);
                startActivity(intent);
            }
        });

    }

}
