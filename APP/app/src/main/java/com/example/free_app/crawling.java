/* gradle에 implementation 'org.jsoup:jsoup:1.13.1' 추가!!*/

package com.example.imagesearch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    String nums;                    //이름 저장할 변수
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.result);
        final Bundle bundle = new Bundle();

        new Thread(){
            @Override
            public void run() {
                Document doc = null;
                try {
                    doc = Jsoup.connect("https://www.google.com/search?tbs=sbi:AMhZZisnZcoRrCBK3kS5iTlAgs9Lyg3ZRXQBXOnUAjDTUQVKmBehbh2AZLqBE-KLxyEValvo9w0Wlq7GMTGlRGh-XlUfXdaj2hovyzpO7cnwJ3XRxR0B3FuWy0rVrKGqUb9PzZ8nQiE2-cRmhlDRrwEUnP9C01J4ZcueJWvyc8a5R7UCyV6OxYu5PiGbXXVqJczGg_1vo0Ok_1KrZ5fsu6hB7XB0C1BWONuAbAnzoj5AxoLZG7EteCW0C7FzS6V14d3GQuHU2csVXCz4ZgThcvR7LekNjuOPLugUkuFswe5T-aMyPKj8SRTnxzqgwO8Jw6itQhUA9jB38DwSYUqa1UBAajPi0_1RElE4Q&hl=ko").get();
                    Elements contents = doc.select(".fKDtNb");          //class값 가져오기
                    nums = contents.text();

                    /*for(int i = 1; i < 7; i++){
                        contents = doc.select("#drwtNo"+i);                 //복권 번호 6개 가져오기
                        nums += " "+contents.text();
                    }
                    nums += doc.select("#bnusNo").text();  */                 //보너스 번호 contents 변수를 사용하지 않고 가져오는 방법

                    bundle.putString("numbers", nums);                               //핸들러를 이용해서 Thread()에서 가져온 데이터를 메인 쓰레드에 보내준다.
                    Message msg = handler.obtainMessage();
                    msg.setData(bundle);
                    handler.sendMessage(msg);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            Bundle bundle = msg.getData();
            textView.setText(bundle.getString("numbers"));                      //이런식으로 View를 메인 쓰레드에서 뿌려줘야한다.
        }
    };
}


