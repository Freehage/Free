package com.example.free_app.recycleTip;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class TipData {
    private String Title;
    private String detail;
    private String img_url;
    private String page_url;

    public TipData(String Title, String detail, String img_url, String page_url){
        this.Title = Title;
        this.detail = detail;
        this.img_url = img_url;
        this.page_url = page_url;
    }

    public String getTitle(){
        return Title;
    }
    public String getDetail(){
        return detail;
    }
    public String getImg_url() {
        return img_url;
    }
    public String getPage_url() {
        return page_url;
    }
    public void setTitle(String Title){
        this.Title = Title;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

}
