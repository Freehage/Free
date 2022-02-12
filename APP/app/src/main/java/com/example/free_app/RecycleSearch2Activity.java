package com.example.free_app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class RecycleSearch2Activity extends AppCompatActivity {

    private List<String> list;          // 데이터를 넣은 리스트변수
    private ListView listView;          // 검색을 보여줄 리스트변수
    private EditText editSearch;        // 검색어를 입력할 Input 창
    private SearchAdapter adapter;      // 리스트뷰에 연결할 아답터
    private ArrayList<String> arraylist;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycleaftersearch);

        editSearch = (EditText) findViewById(R.id.editSearch);
        listView = (ListView) findViewById(R.id.listView);

        // db 리스트를 생성한다.
        list = new ArrayList<String>();



        



        Intent intent = getIntent();
        String name = intent.getStringExtra("name");

        editSearch = (EditText) findViewById(R.id.editSearch);
        if (editSearch == null){
            editSearch.setText(name);
        }

        listView = (ListView) findViewById(R.id.listView);








    }



}
