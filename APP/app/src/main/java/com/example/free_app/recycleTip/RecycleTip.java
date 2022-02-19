package com.example.free_app.recycleTip;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.free_app.MainActivity;
import com.example.free_app.R;
import com.example.free_app.recycleTip.TipAdapter;
import com.example.free_app.recycleTip.TipData;

import java.util.ArrayList;

public class RecycleTip extends AppCompatActivity {
    private ArrayList<TipData> arrayList;
    private TipAdapter adapter;

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


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycletip);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_space);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#000000'>재활용 팁 알아보기 </font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        this.Initialize();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new TipAdapter(arrayList,this);
        recyclerView.setAdapter(adapter);

    }

    private void Initialize() {
        arrayList = new ArrayList<>();
        arrayList.add(new TipData("아이스팩 재활용 꿀Tip! 그냥 버리지 마세요!",
                "대부분 물이라 생각할 수 있지만, '고흡수성 폴리머'는 물과 만나 젤리 형태로 변하게 됩니다. 아이스팩 이렇게 버리세요!",
                "https://search.pstatic.net/common/?src=http%3A%2F%2Fblogfiles.naver.net%2FMjAyMTEwMTRfODUg%2FMDAxNjM0MTk1MTUzMDEz.hk6v1D14w6iZMCrlNEtRl0Fpu81qSvBGKX3LSu0K_GQg.A-yxYgIyrGVWGX4CDz-Fo30mXchK2bbhZBOU5ezlVYMg.PNG.ddccity%2F%25C0%25CF%25BB%25F3%25B2%25DC%25C1%25A4%25BA%25B8.png%23500x500&type=ff264_180"
                ,"https://blog.naver.com/ddccity/222536853868/"));
        arrayList.add(new TipData("[해랑]해운대구, 올바른 분리수거 재활용 Tip",
                "평소 헷갈렸던 분리배출 품목이나 잘못 알고 있었던 분리배출 방법이 있으시면 이번 기회에 제대로 알아가시면 좋을 것 같습니다",
                "https://postfiles.pstatic.net/MjAyMTA0MjBfNSAg/MDAxNjE4ODgxNTE2Nzk0.KbU_Xy9Ean5p8lw6xI1eUWafITCzd5yVKBUrB7Hmy2Mg.tw25Y11edJkrLDNC4qaWu-OpzNH1-NFr-6Z40HLfYi4g.PNG.hudpr/%ED%95%B4%EB%9E%91_%EC%84%AC%EB%84%A4%EC%9D%BC.png?type=w966",
                "https://blog.naver.com/hudpr/222316400370/"));
        arrayList.add(new TipData("물티슈 뚜껑 활용 방법에 대해 알아보자!",
                "그냥 버리기 쉬운 물티슈의 뚜껑을 활용한 6가지 재사용 방법.",
                "https://postfiles.pstatic.net/MjAyMTA0MjBfNSAg/MDAxNjE4ODgxNTE2Nzk0.KbU_Xy9Ean5p8lw6xI1eUWafITCzd5yVKBUrB7Hmy2Mg.tw25Y11edJkrLDNC4qaWu-OpzNH1-NFr-6Z40HLfYi4g.PNG.hudpr/%ED%95%B4%EB%9E%91_%EC%84%AC%EB%84%A4%EC%9D%BC.png?type=w966",
                "https://topsecret123.tistory.com/831"));
        arrayList.add(new TipData("매일마다 먹는 커피의 컵 홀더 활용법!",
                "하루에도 여러 개 생기는 컵 홀더를 그냥 버리지 말고 색다르게 재활용해보세요.",
                "https://postfiles.pstatic.net/MjAyMTA0MjBfNSAg/MDAxNjE4ODgxNTE2Nzk0.KbU_Xy9Ean5p8lw6xI1eUWafITCzd5yVKBUrB7Hmy2Mg.tw25Y11edJkrLDNC4qaWu-OpzNH1-NFr-6Z40HLfYi4g.PNG.hudpr/%ED%95%B4%EB%9E%91_%EC%84%AC%EB%84%A4%EC%9D%BC.png?type=w966",
                "https://sharehows.com/60367/"));
        arrayList.add(new TipData("지구를 사랑하기 위한 또 하나의 방법!",
                "일상 속에서 제로웨이스트 샵을 통해 손쉽게 실천 가능한 친환경 활동에  동참하세요.",
                "https://postfiles.pstatic.net/MjAyMTA0MjBfNSAg/MDAxNjE4ODgxNTE2Nzk0.KbU_Xy9Ean5p8lw6xI1eUWafITCzd5yVKBUrB7Hmy2Mg.tw25Y11edJkrLDNC4qaWu-OpzNH1-NFr-6Z40HLfYi4g.PNG.hudpr/%ED%95%B4%EB%9E%91_%EC%84%AC%EB%84%A4%EC%9D%BC.png?type=w966",
                "https://post.naver.com/viewer/postView.naver?volumeNo=32955935&memberNo=36054406&vType=VERTICAL/"));

        arrayList.add(new TipData("버리기도 애매한 옷걸이 활용 살림 꿀팁!",
                "분리수거하기도 애매해서 그냥 쓰레기로 버리기 급급한 옷걸이의 색다른 활용법",
                "https://postfiles.pstatic.net/MjAyMTA0MjBfNSAg/MDAxNjE4ODgxNTE2Nzk0.KbU_Xy9Ean5p8lw6xI1eUWafITCzd5yVKBUrB7Hmy2Mg.tw25Y11edJkrLDNC4qaWu-OpzNH1-NFr-6Z40HLfYi4g.PNG.hudpr/%ED%95%B4%EB%9E%91_%EC%84%AC%EB%84%A4%EC%9D%BC.png?type=w966",
                "https://blog.naver.com/feelintouch9/222572594564/"));

    }
}
