package com.example.free_app.after_search;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
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

public class OcrAdapter extends RecyclerView.Adapter<OcrAdapter.ViewHolder>{

    private ArrayList productArrayList;
    private Context mcontext;
    private DatabaseHelper3 mDBHelper;

    public OcrAdapter(Context context, ArrayList<Product> dataList){

        this.mcontext = context;
        productArrayList = dataList;
        mDBHelper = new DatabaseHelper3(context);


    }
    @NonNull
    @Override
    public OcrAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_recommend2, parent, false);
        return new OcrAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OcrAdapter.ViewHolder holder, int position) {
        String object = productArrayList.get(position).toString();
        Log.e("OBJET",object);
        holder.recom_Title1.setText(object);
        String level = mDBHelper.getLevel(object);
        holder.recom_detail1.setText("탄소 중립 LEVEL: " + level);
        String amount = mDBHelper.getCarbon(object);
        holder.recom_detail2.setText("탄소 배출량: " + amount);
        holder.recom_img1.setImageResource(R.drawable.main);
        String company = mDBHelper.getCompanyResult(object);
        String end_date = mDBHelper.getEndDate(object);
        String recycle = mDBHelper.getRecycle(object);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mcontext, SearchResultActivity.class);
                intent.putExtra("company",company);
                intent.putExtra("object",object);
                intent.putExtra("level",level);
                intent.putExtra("end_date",end_date);
                intent.putExtra("recycle_category",recycle);
                intent.putExtra("carbon_amount",amount);
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

            recom_Title1 = itemView.findViewById(R.id.recom_Title2);
            recom_detail1 = itemView.findViewById(R.id.recom_detail12);
            recom_detail2 = itemView.findViewById(R.id.recom_detail22);
            recom_img1 = itemView.findViewById(R.id.recom_img2);

        }
    }
}
