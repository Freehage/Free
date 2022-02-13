package com.example.free_app.after_search;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.free_app.R;
import com.example.free_app.database.DatabaseHelper;
import com.example.free_app.model.Product;

import java.util.ArrayList;

public class RecommendAdapter extends RecyclerView.Adapter<RecommendAdapter.ViewHolder>{

    private ArrayList productArrayList;
    private Context mcontext;
    private DatabaseHelper mDBHelper;

    public RecommendAdapter(ArrayList<Product> dataList, Context context){

        this.mcontext = context;
        productArrayList = dataList;

        mDBHelper = new DatabaseHelper(context);


    }
    @NonNull
    @Override
    public RecommendAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_recommend, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecommendAdapter.ViewHolder holder, int position) {

        String object = productArrayList.get(position).toString();
        holder.recom_Title1.setText(object);
        //탄소중립 레벨
        String level = mDBHelper.getLevel(object);
        holder.recom_detail1.setText("탄소 중립 LEVEL: " + level);
        //탄소 배출량 : getOBOUTC
        String amount = mDBHelper.getCarbon(object);
        holder.recom_detail2.setText("탄소 배출량: " + amount);
        holder.recom_img1.setImageResource(R.mipmap.ic_launcher);

    }

    @Override
    public int getItemCount() {
        return productArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView recom_Title1;
        TextView recom_detail1;
        TextView recom_detail2;
        ImageView recom_img1;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            recom_Title1 = itemView.findViewById(R.id.recom_Title);
            recom_detail1 = itemView.findViewById(R.id.recom_detail);
            recom_detail2 = itemView.findViewById(R.id.recom_detail2);
            recom_img1 = itemView.findViewById(R.id.recom_img);

        }
    }
}
