package com.example.free_app.recycleTip;

import android.view.View;

import com.example.free_app.recycleTip.TipAdapter;

public interface OnPersonItemClickListener {
    public void onItemClick(TipAdapter.ViewHolder holder, View view, int position);
}
