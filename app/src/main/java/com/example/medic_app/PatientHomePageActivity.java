package com.example.medic_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class PatientHomePageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        ImageView im=(ImageView)findViewById(R.id.PatientProfilePic);
        im.setImageResource(R.drawable.patient1);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.UpcomingConsultationContainer, new PatientUpcomingConsultationsFragment());
        fragmentTransaction.replace(R.id.RecentConsultationContainer,new PatientRecentConsultationsFragment());
        fragmentTransaction.commit();
        im.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PatientHomePageActivity.this,ProfilePageActivity.class));
            }
        });

    }
}