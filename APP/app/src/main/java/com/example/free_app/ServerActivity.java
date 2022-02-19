package com.example.free_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class ServerActivity extends AppCompatActivity {
    private Double carbon, score, price;
    @Override
    protected void onCreate(Bundle savedInstanceState) { // 액티비티 시작시 처음으로 실행되는 생명주기!
        super.onCreate(savedInstanceState);
        carbon = ((MainActivity)MainActivity.main_context).Carbon;
        score = ((MainActivity)MainActivity.main_context).Score;
        price = ((MainActivity)MainActivity.main_context).Price;


        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.getBoolean("success");
                    if (success) {
                        Toast.makeText(getApplicationContext(), "저장 완료!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "다시 한번 시도해주세요", Toast.LENGTH_SHORT).show();
                        return;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        };
        // 서버로 Volley를 이용해서 요청
        ServerRequest serverRequest = new ServerRequest(carbon,score,price,responseListener);
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(serverRequest);

        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);


    }
}


