package com.example.free_app.model;

public class ZeroShop {

    public int _id;
    public String storeName;
    public double latitude;
    public double longitude;
    public String detail;
    public String address;
    public String url;

    // set function
    public void setId(int _id){ this._id = _id; }
    public void setStoreName(String storeName){ this.storeName = storeName; }
    public void setLatitude(double latitude){ this.latitude = latitude; }
    public void setLongitude(double longitude){ this.longitude = longitude; }
    public void setDetail(String detail){ this.detail = detail; }
    public void setAddress(String address){ this.address = address; }
    public void setUrl(String url){ this.url = url; }

    // get function
    public int getId(){ return this._id; }
    public String getStoreName(){ return this.storeName; }
    public String getDetail(){ return this.detail; }
    public double getLatitude(){ return this.latitude; }
    public double getLongitude(){ return this.longitude; }
    public String getAddress(){ return this.address; }
    public String getUrl(){ return this.url; }
}
