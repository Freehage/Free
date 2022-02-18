package com.example.free_app.after_search;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.free_app.MainActivity;
import com.example.free_app.R;
import com.example.free_app.database.DatabaseHelper3;

import java.util.ArrayList;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder>
        implements OnPersonItemClickListener2 {
    private ArrayList<String> productArrayList;
    OnPersonItemClickListener2 listener;
    private Context mcontext;
    private DatabaseHelper3 mDBHelper;

    public SearchAdapter(ArrayList<String> dataList, Context context){

        this.mcontext = context;
        productArrayList = dataList;

        mDBHelper = new DatabaseHelper3(context);
    }


    @Override
    public SearchAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemview = inflater.inflate(R.layout.activity_aftersearchdata,parent,false);
        SearchAdapter.ViewHolder viewHolder = new SearchAdapter.ViewHolder(itemview,this);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(SearchAdapter.ViewHolder holder, int position) {
        String object = productArrayList.get(position).toString();
        String company = mDBHelper.getCompanyResult(object);
        String level = mDBHelper.getLevel(object);
        String end_date = mDBHelper.getEndDate(object);
        String recycle = mDBHelper.getRecycle(object);
        String money = mDBHelper.getMoney(object);
        String score = mDBHelper.getScore(object);
        String amount = mDBHelper.getCarbon(object);


        holder.object1.setText(object);
        holder.recycle1.setText(recycle);
        if(((MainActivity)MainActivity.main_context).num == 0){
            holder.level1.setText("탄소 중립 LEVEL: " + level);
        }
        else if(((MainActivity)MainActivity.main_context).num == 1){
            holder.level1.setText("탄소 배출량 " + amount);
        }
        else if(((MainActivity)MainActivity.main_context).num == 2){
            holder.level1.setText("가격: " + money);
        }
        else if(((MainActivity)MainActivity.main_context).num == 3){
            holder.level1.setText("평점 " + score);
        }
        holder.img1.setImageResource(R.mipmap.ic_launcher);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mcontext, SearchResultActivity.class);
                intent.putExtra("company",company);
                intent.putExtra("object",object);
                intent.putExtra("level",level);
                intent.putExtra("end_date",end_date);
                intent.putExtra("carbon_amount",amount);
                intent.putExtra("recycle_category",recycle);
                mcontext.startActivity(intent.addFlags(FLAG_ACTIVITY_NEW_TASK));
            }
        });


    }

    @Override
    public int getItemCount() {
        return productArrayList.size();
    }

    public void setOnItemClicklistener(OnPersonItemClickListener2 listener){
        this.listener = listener;
    }
    @Override
    public void onItemClick(SearchAdapter.ViewHolder holder, View view, int position) {
        if(listener != null){
            listener.onItemClick(holder,view,position);
        }
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView object1;
        TextView level1;
        TextView recycle1;
        ImageView img1;

        public ViewHolder(@NonNull View itemView,final OnPersonItemClickListener2 listener) {
            super(itemView);

            object1 = itemView.findViewById(R.id.txt_objname);
            recycle1 = itemView.findViewById(R.id.txt_recycle);
            level1 = itemView.findViewById(R.id.txt_level);
            img1 = itemView.findViewById(R.id.img1);
        }
    }


}
