package com.example.medic_app;

public class Doctor {
    public String id;
    public String name;
    public int dp;
    public Doctor(String name,String id, int imgid){
        this.name=name;
        this.id=id;
        this.dp=imgid;
    }
    public String getName() {
        return name;
    }
    public int getDp() {
        return dp;
    }
    public String getId() {
        return id;
    }
}
