package com.example.free_app.after_search;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.free_app.MainActivity;
import com.example.free_app.R;
import com.example.free_app.database.DatabaseHelper;
import com.example.free_app.model.Product;

import java.util.ArrayList;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder>
        implements OnPersonItemClickListener2 {
    private ArrayList<Product> productArrayList;
    private ArrayList<Product> product_COMArrayList;
    OnPersonItemClickListener2 listener;
    private Context mcontext;
    private DatabaseHelper mDBHelper;

    public SearchAdapter(ArrayList<Product> dataList, Context context){

        this.mcontext = context;
        productArrayList = dataList;

        mDBHelper = new DatabaseHelper(context);
        Log.e("LLL",productArrayList.toString());
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
        holder.object1.setText(productArrayList.get(position).getObject());
        holder.level1.setText("탄소 중립 LEVEL: " + Integer.toString(productArrayList.get(position).getOblevel()));
        //String company_level = mDBHelper.getLevel(productArrayList.get(position).getObject());
        //holder.level1.setText("탄소 중립 레벨: " + company_level);
        holder.img1.setImageResource(R.mipmap.ic_launcher);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mcontext, SearchResultActivity.class);
                mcontext.startActivity(intent);
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
        ImageView img1;

        public ViewHolder(@NonNull View itemView,final OnPersonItemClickListener2 listener) {
            super(itemView);

            object1 = itemView.findViewById(R.id.txt_objname);
            level1 = itemView.findViewById(R.id.txt_level);
            img1 = itemView.findViewById(R.id.img1);
        }
    }


}
