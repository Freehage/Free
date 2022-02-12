package com.example.free_app.recycleTip;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.free_app.R;

import java.util.ArrayList;

public class TipAdapter extends RecyclerView.Adapter<TipAdapter.ViewHolder>
        implements OnPersonItemClickListener {

    private ArrayList<TipData> dataArrayList = null;
    OnPersonItemClickListener listener;
    private Context mcontext;

    public TipAdapter(ArrayList<TipData> dataList, Context context){

        this.mcontext = context;
        dataArrayList = dataList;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemview = inflater.inflate(R.layout.activity_recycletipdata,parent,false);
        ViewHolder viewHolder = new ViewHolder(itemview,this);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.Title.setText(dataArrayList.get(position).getTitle());
        holder.detail.setText(dataArrayList.get(position).getDetail());
        String url = dataArrayList.get(position).getImg_url();
        Glide.with(holder.itemView.getContext()).load(url).into(holder.imgurl);

        String page_url = dataArrayList.get(position).getPage_url();

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(page_url));
                mcontext.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return dataArrayList.size();
    }

    public void setOnItemClicklistener(OnPersonItemClickListener listener){
        this.listener = listener;
    }
    @Override
    public void onItemClick(ViewHolder holder, View view, int position) {
        if(listener != null){
            listener.onItemClick(holder,view,position);
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView Title;
        TextView detail;
        ImageView imgurl;

        public ViewHolder(@NonNull View itemView,final OnPersonItemClickListener listener) {
            super(itemView);
            Title = itemView.findViewById(R.id.Title);
            detail = itemView.findViewById(R.id.detail);
            imgurl = itemView.findViewById(R.id.imgurl);

        }
    }

    public TipData getItem(int position){
        return dataArrayList.get(position);
    }


}

