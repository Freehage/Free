package com.example.free_app;

public class TipData {
    private String Title;
    private String detail;
    private int img_url;

    public TipData(String Title, String detail, int img_url){
        this.Title = Title;
        this.detail = detail;
        this.img_url = img_url;
    }

    public String getTitle(){
        return Title;
    }
    public String getDetail(){
        return detail;
    }
    public int getImg_url(){
        return img_url;
    }
    public void setTitle(String Title){
        this.Title = Title;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public void setImg_url(int img_url) {
        this.img_url = img_url;
    }
}
