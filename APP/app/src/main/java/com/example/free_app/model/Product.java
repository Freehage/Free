package com.example.free_app.model;

public class Product {

    private int id;
    private String object;
    private String obclass;

    public String getObclass() {return obclass;}

    public void setObclass(String obclass) {this.obclass = obclass; }

    private String company;
    private String obline;
    private String obrecy;
    private int oblevel;
    private String oboutC;
    private String obendday;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getObline() {
        return obline;
    }

    public void setObline(String obline) {
        this.obline = obline;
    }

    public String getObrecy() {
        return obrecy;
    }

    public void setObrecy(String obrecy) {
        this.obrecy = obrecy;
    }

    public int getOblevel() {
        return oblevel;
    }

    public void setOblevel(int oblevel) {
        this.oblevel = oblevel;
    }

    public String getOboutC() {
        return oboutC;
    }

    public void setOboutC(String oboutC) {
        this.oboutC = oboutC;
    }

    public String getObendday() {
        return obendday;
    }

    public void setObendday(String obendday) {
        this.obendday = obendday;
    }


}
