package com.example.medic_app;

public class DeveloperModel {
    private String developername;
    private String developeremail;
    private String developerwork;
    private int developerimage;

    public DeveloperModel(String developername, String developeremail, String developerwork, int developerimage)
    {
        this.developername = developername;
        this.developeremail = developeremail;
        this.developerwork = developerwork;
        this.developerimage = developerimage;
    }

    public String getDevelopername() {
        return developername;
    }

    public void setDevelopername(String developername) {
        this.developername = developername;
    }

    public String getDeveloperemail() {
        return developeremail;
    }

    public void setDeveloperemail(String developeremail) {
        this.developeremail = developeremail;
    }

    public String getDeveloperwork() {
        return developerwork;
    }

    public void setDeveloperwork(String developerwork) {
        this.developerwork = developerwork;
    }

    public int getDeveloperimage() {
        return developerimage;
    }

    public void setDeveloperimage(int developerimage) {
        this.developerimage = developerimage;
    }
}
