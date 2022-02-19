package com.example.free_app.after_search;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.free_app.R;
import com.example.free_app.database.DatabaseHelper3;
import com.example.free_app.model.Product;

import java.util.ArrayList;

public class RecommendAdapter extends RecyclerView.Adapter<RecommendAdapter.ViewHolder>{

    private ArrayList productArrayList;
    private Context mcontext;
    private DatabaseHelper3 mDBHelper;
    private ArrayList<String> backdatalist;

    public RecommendAdapter(ArrayList<Product> dataList, ArrayList<String> backlist, Context context){

        this.mcontext = context;
        productArrayList = dataList;
        backdatalist = backlist;
        mDBHelper = new DatabaseHelper3(context);


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
        String amount = mDBHelper.getCarbon(object);
        String company = mDBHelper.getCompanyResult(object);
        String end_date = mDBHelper.getEndDate(object);
        String recycle = mDBHelper.getRecycle(object);
        String url = mDBHelper.getUrl(object);

        if(level.contains("0")){
            holder.recom_detail1.setText("해당 제품은 저탄소 제품 인증 마크가 없는 제품입니다");
            holder.recom_detail2.setText(" ");
        }else{
            holder.recom_detail1.setText("탄소 중립 LEVEL: " + level);
            holder.recom_detail2.setText("탄소 배출량: " + amount);
        }
        holder.recom_img1.setImageResource(R.drawable.main);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mcontext, ClickRecommend.class);
                intent.putExtra("company",company);
                intent.putExtra("object",object);
                intent.putExtra("level",level);
                intent.putExtra("end_date",end_date);
                intent.putExtra("carbon_amount",amount);
                intent.putExtra("recycle_category",recycle);
                intent.putExtra("backlist",backdatalist);
                intent.putExtra("url",url);
                mcontext.startActivity(intent);
            }
        });

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
