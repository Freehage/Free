package com.example.free_app;

import android.app.Person;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecycleTip extends AppCompatActivity {
    private ArrayList<TipData> arrayList;
    private TipAdapter adapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycletip);
        this.Initialize();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new TipAdapter(arrayList,this);
        recyclerView.setAdapter(adapter);

    }

    private void Initialize() {
        arrayList = new ArrayList<>();
        arrayList.add(new TipData("아이스팩 재활용 꿀Tip! 그냥 버리지 마세요~!",
                "[아이스팩 재활용 꿀Tip! 그냥 버리지 마세요~!] 코로나19 확산방지를 위해 가정에서 머무는 시간이 늘어나면서 포장과 배달을 지향하는 분위기가 형성되었습니다...",
                "https://search.pstatic.net/common/?src=http%3A%2F%2Fblogfiles.naver.net%2FMjAyMTEwMTRfODUg%2FMDAxNjM0MTk1MTUzMDEz.hk6v1D14w6iZMCrlNEtRl0Fpu81qSvBGKX3LSu0K_GQg.A-yxYgIyrGVWGX4CDz-Fo30mXchK2bbhZBOU5ezlVYMg.PNG.ddccity%2F%25C0%25CF%25BB%25F3%25B2%25DC%25C1%25A4%25BA%25B8.png%23500x500&type=ff264_180"
                ,"https://blog.naver.com/ddccity/222536853868/"));
        arrayList.add(new TipData("[해랑] 해운대구, 올바른 분리수거 재활용 Tip",
                "분리수거, 재활용에 대한 관심도 높아지고 있는데요. #분리배출 자체가 소각할 쓰레기와 재활용할 물품을... 그래서 오늘은 해운대구에서 시행 중인 올바른 #분리수거...",
                "https://postfiles.pstatic.net/MjAyMTA0MjBfNSAg/MDAxNjE4ODgxNTE2Nzk0.KbU_Xy9Ean5p8lw6xI1eUWafITCzd5yVKBUrB7Hmy2Mg.tw25Y11edJkrLDNC4qaWu-OpzNH1-NFr-6Z40HLfYi4g.PNG.hudpr/%ED%95%B4%EB%9E%91_%EC%84%AC%EB%84%A4%EC%9D%BC.png?type=w966",
                "https://blog.naver.com/hudpr/222316400370/"));

    }
}
