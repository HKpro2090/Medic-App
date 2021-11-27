package com.example.medic_app;

public class Doctor {
    public String id;
    public String name;
    public String department;
    public int dp;
    public Doctor(String name,String id,String department, int imgid){
        this.name=name;
        this.id=id;
        this.dp=imgid;
        this.department=department;
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
    public String getDepartment() {return department;}
}
