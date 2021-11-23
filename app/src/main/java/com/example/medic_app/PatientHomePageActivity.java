package com.example.medic_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class PatientHomePageActivity extends AppCompatActivity {

    boolean doubleBackToExitPressedOnce = false;
    TextView pagetitle;
    BottomNavigationView bnv;


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
        //Code related to bottom navigation bar

        FloatingActionButton b=(FloatingActionButton)findViewById(R.id.addbutton);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(getApplicationContext(),NewConsultationActivity.class);
                startActivity(in);
            }
        });

        bnv = (BottomNavigationView) findViewById(R.id.bottombar);
        bnv.setSelectedItemId(R.id.miHome);
        bnv.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener(){
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item){
                switch (item.getItemId())
                {
                    case (R.id.miHome):
                        Toast.makeText(getApplicationContext(),"Home",Toast.LENGTH_LONG).show();


                    case (R.id.miConsultation):
                        Toast.makeText(getApplicationContext(),"Consulatation",Toast.LENGTH_LONG).show();
                }
                return true;
            }
        });

    }
    //Code to press back twice to exit.
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

    public void home_to_consultation()
    {
        pagetitle.setText("Consultations");
    }

    public void consultation_to_home()
    {
        pagetitle.setText("Home");
    }

}