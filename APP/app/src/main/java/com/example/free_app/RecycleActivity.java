package com.example.free_app;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.free_app.recycleTip.RecycleTip;

public class RecycleActivity extends AppCompatActivity {
    Button recycle_1, recycle_2, recycle_3;

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
        setContentView(R.layout.activity_recycle);


        //View myButtonLayout = getLayoutInflater().inflate(R.layout.action_bar,null);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_space);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#000000'>재활용 실천하기 </font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        recycle_1 = findViewById(R.id.recycle_1);
        recycle_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),Recycle1Activity.class);
                startActivity(intent);

            }
        });

        recycle_2 = findViewById(R.id.recycle_2);
        recycle_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RecycleTip.class);
                startActivity(intent);

            }
        });

        recycle_3 = findViewById(R.id.recycle_3);
        recycle_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),ZerowasteActivity.class);
                startActivity(intent);

            }
        });

    }
}



