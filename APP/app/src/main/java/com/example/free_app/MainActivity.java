package com.example.free_app;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;

import com.example.free_app.after_search.MainSearchActivity;
import com.example.free_app.cardnews.CustomAdapter;
import com.example.free_app.database.DatabaseHelper3;
import com.example.free_app.model.Product;

import java.util.List;

import me.relex.circleindicator.CircleIndicator3;

public class MainActivity extends AppCompatActivity {

    // camera
    private static final int REQUEST_IMAGE_CODE = 101;

    public static Context main_context;
    public int card_num = 0;

    private Button photo;
    private Button recycle;
    private ViewPager2 viewPager2;
    private FragmentStateAdapter fragmentStateAdapter;
    private int page = 4;
    private CircleIndicator3 circleIndicator3;
    private SearchView searchView;
    public List<Product> productlist;
    public int num = 0;

    public ImageView logo;
    Dialog dilaog01;

    public double Price = 0;
    public double Carbon = 0;
    public double Score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        main_context = MainActivity.this;
        initLoadDB();

        openOrCreateDatabase("FreeAppDB.db", MODE_PRIVATE, null);
        // db.close() -- DO NOT USE THIS

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();


        dilaog01 = new Dialog(MainActivity.this);       // Dialog 초기화
        dilaog01.setContentView(R.layout.dialog_survey);


        logo = (ImageView) findViewById(R.id.imageView);
        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog01();
            }
        });



        viewPager2 = findViewById(R.id.viewpager);
        fragmentStateAdapter = new CustomAdapter(MainActivity.this, page);
        viewPager2.setAdapter(fragmentStateAdapter);
        circleIndicator3 = findViewById(R.id.indicator);
        circleIndicator3.setViewPager(viewPager2);
        circleIndicator3.createIndicators(page,0);
        viewPager2.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);

        viewPager2.setCurrentItem(0);
        viewPager2.setOffscreenPageLimit(4);
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                if(positionOffsetPixels == 0){
                    viewPager2.setCurrentItem(position);
                }
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                circleIndicator3.animatePageSelected(position%page);
            }
        });

        //search
        searchView = (SearchView) findViewById(R.id.search_bar);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                Intent intent_mainsearch = new Intent(getApplicationContext(), MainSearchActivity.class);
                intent_mainsearch.putExtra("search_name",s);
                startActivity(intent_mainsearch);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        // 제품 촬영 버튼 클릭.
        photo = (Button) findViewById(R.id.photo);
        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_searchob = new Intent(getApplicationContext(), AfterDetectActivity.class);
                startActivity(intent_searchob);
                // takePicture();
            }
        });

        // 재활용 버튼 클릭.
        recycle = (Button) findViewById(R.id.recycle);
        recycle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_recycle = new Intent(getApplicationContext(), RecycleActivity.class);
                startActivity(intent_recycle);
            }
        });

    }


    //다이얼로그

    private void showDialog01() {
        dilaog01.show(); // 다이얼로그 띄우기

        //가격
        Button Btn11 = dilaog01.findViewById(R.id.rg_btn11);
        Btn11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { Price = 0.1; }});

        Button Btn12 = dilaog01.findViewById(R.id.rg_btn12);
        Btn12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { Price = 0.2; }});

        Button Btn13 = dilaog01.findViewById(R.id.rg_btn13);
        Btn13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { Price = 0.3; }});

        Button Btn14 = dilaog01.findViewById(R.id.rg_btn14);
        Btn14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { Price = 0.4; }});

        Button Btn15 = dilaog01.findViewById(R.id.rg_btn15);
        Btn15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { Price = 0.5; }});

        //환경

        Button Btn21 = dilaog01.findViewById(R.id.rg_btn21);
        Btn21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { Carbon = 0.1; }});

        Button Btn22 = dilaog01.findViewById(R.id.rg_btn22);
        Btn22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { Carbon  = 0.2; }});

        Button Btn23 = dilaog01.findViewById(R.id.rg_btn23);
        Btn23.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { Carbon  = 0.3; }});

        Button Btn24 = dilaog01.findViewById(R.id.rg_btn24);
        Btn24.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { Carbon  = 0.4; }});

        Button Btn25 = dilaog01.findViewById(R.id.rg_btn25);
        Btn25.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { Carbon  = 0.5; }});

        //평점

        Button Btn31 = dilaog01.findViewById(R.id.rg_btn31);
        Btn31.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { Score = 0.1; }});

        Button Btn32 = dilaog01.findViewById(R.id.rg_btn32);
        Btn32.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { Score  = 0.2; }});

        Button Btn33 = dilaog01.findViewById(R.id.rg_btn33);
        Btn23.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { Score  = 0.3; }});

        Button Btn34 = dilaog01.findViewById(R.id.rg_btn34);
        Btn34.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { Score  = 0.4; }});

        Button Btn35 = dilaog01.findViewById(R.id.rg_btn35);
        Btn35.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { Score  = 0.5; }});

        Button Check = dilaog01.findViewById(R.id.btn_done);
        Check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),ServerActivity.class);
                dilaog01.dismiss();
                startActivity(intent);
            }  });

    }




    private void initLoadDB(){
        DatabaseHelper3 databaseHelper = new DatabaseHelper3(getApplicationContext());
        databaseHelper.openDB();

        productlist = databaseHelper.getTableData();
        Log.e("TEST",String.valueOf(productlist.size()));
        databaseHelper.close();
    }

    // camera 사진 찍기.
    public void takePicture() {
        Intent imageTakeIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if(imageTakeIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(imageTakeIntent, REQUEST_IMAGE_CODE);
        }
    }

}