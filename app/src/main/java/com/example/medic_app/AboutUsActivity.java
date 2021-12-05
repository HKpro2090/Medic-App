package com.example.medic_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class AboutUsActivity extends AppCompatActivity {
    private RecyclerView developerRV;
    private ArrayList<DeveloperModel> developerModelArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        developerRV = findViewById(R.id.idRVdeveloper);

        developerModelArrayList = new ArrayList<>();
        developerModelArrayList.add(new DeveloperModel("Gautam P A","gautampa7@gmail.com","Firebase Integration",R.drawable.gautamdp));
        developerModelArrayList.add(new DeveloperModel("Ajitesh J S","ajiteshjs2001@gmail.com","Backend Development",R.drawable.ajiteshdp));
        developerModelArrayList.add(new DeveloperModel("Harikrishnan V","harikrishnan.vamsi@gmail.com","Frontend Deveopment",R.drawable.haridp));
        DeveloperAdapter developerAdapter = new DeveloperAdapter(this, developerModelArrayList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        developerRV.setLayoutManager(linearLayoutManager);
        developerRV.setAdapter(developerAdapter);


    }
}