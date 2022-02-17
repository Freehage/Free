package com.example.free_app.after_search;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.free_app.R;
import com.example.free_app.model.Product;

import java.util.ArrayList;

public class OcrAdapter extends BaseAdapter {
    Context mContext = null;
    LayoutInflater mLayoutInflater = null;
    ArrayList<Product> sample;

    public OcrAdapter(Context context, ArrayList<Product> data) {

        mContext = context;
        sample = data;
        mLayoutInflater = LayoutInflater.from(mContext);
        Log.e("ㅎㅎㅎㅎㅎ","????????????");


    }

    public int getCount() {
        return sample.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Product getItem(int position) {
        return sample.get(position);
    }

    @Override
    public View getView(int position, View converView, ViewGroup parent) {

        View view = mLayoutInflater.inflate(R.layout.activity_ocr,null);

        TextView recom_Title1 = (TextView)view.findViewById(R.id.recom_Title);
        TextView recom_detail1 = (TextView)view.findViewById(R.id.recom_detail);
        TextView recom_detail2 = (TextView)view.findViewById(R.id.recom_detail2);
        //ImageView recom_img1 = (ImageView) view.findViewById(R.id.recom_img);

        //recom_img1.setImageResource(sample.get(position).getObject());
        //Log.e("--", sample.get(position).getObject());
        recom_Title1.setText(sample.get(position).getObject());
        recom_detail1.setText("회사:" +sample.get(position).getCompany());
        recom_detail2.setText("탄소배출량:" +sample.get(position).getOboutC());

        return view;



    }



}
