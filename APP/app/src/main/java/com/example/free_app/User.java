package com.example.free_app;

public class User {

    public int id;
    public String name;
    public String category;
    public int level;
    public int amount;
    public String end_date;

    // set function
    public void setId(int id){ this.id = id; }
    public void setName(String name){ this.name = name; }
    public void setCategory(String category){ this.category = category; }
    public void setLevel(int level){ this.level = level; }
    public void setAmount(int amount){ this.amount = amount; }
    public void setEnd_date(String end_date){ this.end_date = end_date; }

    // get function
    public int getId(){ return this.id; }
    public String getName(){ return this.name; }
    public String getCategory(){ return this.category; }
    public double getLevel(){ return this.level; }
    public double getAmount(){ return this.amount; }
    public String getEnd_date(){ return this.end_date; }

}
