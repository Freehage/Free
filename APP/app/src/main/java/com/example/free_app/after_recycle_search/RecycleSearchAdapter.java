package com.example.free_app.after_recycle_search;

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
import com.example.free_app.Recycle1Activity;
import com.example.free_app.after_search.SearchResultActivity;
import com.example.free_app.database.DatabaseHelper;
import com.example.free_app.model.Product;

import java.util.ArrayList;

public class RecycleSearchAdapter extends RecyclerView.Adapter<RecycleSearchAdapter.ViewHolder>
        implements OnPersonItemClickListener4 {
    private ArrayList<Product> productArrayList;
    OnPersonItemClickListener4 listener;
    private Context mcontext;
    private DatabaseHelper mDBHelper;

    public RecycleSearchAdapter(ArrayList<Product> dataList, Context context){

        this.mcontext = context;
        productArrayList = dataList;

        mDBHelper = new DatabaseHelper(context);
    }


    @Override
    public RecycleSearchAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemview = inflater.inflate(R.layout.activity_aftersearchdata,parent,false);
        RecycleSearchAdapter.ViewHolder viewHolder = new RecycleSearchAdapter.ViewHolder(itemview,this);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(RecycleSearchAdapter.ViewHolder holder, int position) {
        String object = productArrayList.get(position).getObject();
        String recycle_category = productArrayList.get(position).getObrecy();

        holder.object1.setText(object);
        holder.img1.setImageResource(R.mipmap.ic_launcher);
        holder.recycle1.setText(recycle_category);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (recycle_category == "종이"){
                    Intent intent = new Intent(mcontext, AfterRecycleSearch.class);
                    intent.putExtra("recycle_category",recycle_category);
                    mcontext.startActivity(intent);
                }
                else if(recycle_category.equals("종이팩")){
                    Intent intent = new Intent(mcontext, AfterRecycleSearch.class);
                    intent.putExtra("recycle_category",recycle_category);
                    mcontext.startActivity(intent);
                }
                else if(recycle_category.equals("유리")){
                    Intent intent = new Intent(mcontext, AfterRecycleSearch.class);
                    intent.putExtra("recycle_category",recycle_category);
                    mcontext.startActivity(intent);
                }else if(recycle_category.equals("알루미늄")){
                    Intent intent = new Intent(mcontext, AfterRecycleSearch.class);
                    intent.putExtra("recycle_category",recycle_category);
                    mcontext.startActivity(intent);
                }else if(recycle_category.equals("비닐")){
                    Intent intent = new Intent(mcontext, AfterRecycleSearch.class);
                    intent.putExtra("recycle_category",recycle_category);
                    mcontext.startActivity(intent);
                }else if(recycle_category.equals("플라스틱") || recycle_category.equals("페트") ){
                    Intent intent = new Intent(mcontext, AfterRecycleSearch.class);
                    intent.putExtra("recycle_category",recycle_category);
                    mcontext.startActivity(intent);
                }else{
                    Intent intent = new Intent(mcontext, Recycle1Activity.class);
                    mcontext.startActivity(intent);
                }

            }
        });


    }

    @Override
    public int getItemCount() {
        return productArrayList.size();
    }

    public void setOnItemClicklistener(OnPersonItemClickListener4 listener){
        this.listener = listener;
    }
    @Override
    public void onItemClick(RecycleSearchAdapter.ViewHolder holder, View view, int position) {
        if(listener != null){
            listener.onItemClick(holder,view,position);
        }
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView object1;
        ImageView img1;
        TextView recycle1;

        public ViewHolder(@NonNull View itemView,final OnPersonItemClickListener4 listener) {
            super(itemView);

            object1 = itemView.findViewById(R.id.txt_objname);
            recycle1 = itemView.findViewById(R.id.txt_recycle);
            img1 = itemView.findViewById(R.id.img1);
        }
    }


}
