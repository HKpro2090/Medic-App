package com.example.medic_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.ImageView;

public class PatientHomePageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        ImageView im=(ImageView)findViewById(R.id.imageView2);
        im.setImageResource(R.drawable.patient1);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainerView, new PatientUpcomingConsultationsFragment());
        fragmentTransaction.replace(R.id.fragmentContainerView2,new PatientRecentConsultationsFragment());
        fragmentTransaction.commit();

    }
}