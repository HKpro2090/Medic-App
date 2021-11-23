package com.example.medic_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class PatientHomePageActivity extends AppCompatActivity {

    boolean doubleBackToExitPressedOnce = false;
    TextView pagetitle;
    BottomNavigationView bnv;
    FloatingActionButton searchNewBtn;
    FloatingActionButton searchDoctorBtn;
    FloatingActionButton newConsultationBtn;
    TextView SDTV;
    TextView NCTV;
    boolean clicked = false;
    Animation rotateOpen;
    Animation rotateClose;
    Animation toBottom;
    Animation fromBottom;
    Animation toBottomLeft;
    Animation fromBottomleft;



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
        rotateOpen = (Animation) AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_open);
        rotateClose = (Animation) AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_close);
        toBottom = (Animation) AnimationUtils.loadAnimation(getApplicationContext(),R.anim.to_bottom);
        fromBottom = (Animation) AnimationUtils.loadAnimation(getApplicationContext(),R.anim.from_bottom);
        toBottomLeft = (Animation)AnimationUtils.loadAnimation(getApplicationContext(),R.anim.to_bottom_left);
        fromBottomleft = (Animation)AnimationUtils.loadAnimation(getApplicationContext(),R.anim.from_bottom_left);

        FloatingActionButton b=(FloatingActionButton)findViewById(R.id.addbutton);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(getApplicationContext(),NewConsultationActivity.class);
                startActivity(in);
            }
        });

        searchNewBtn = (FloatingActionButton)findViewById(R.id.addbutton);
        searchNewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onAddButtonClicked();
            }
        });
        searchDoctorBtn = (FloatingActionButton)findViewById(R.id.searchdoctorbtn);
        searchDoctorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Search Doctor!",Toast.LENGTH_LONG).show();
            }
        });
        newConsultationBtn = (FloatingActionButton)findViewById(R.id.newconsultationbtn);
        newConsultationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in=new Intent(getApplicationContext(),NewConsultationActivity.class);
                startActivity(in);
            }
        });

        NCTV = (TextView)findViewById(R.id.nctv);
        SDTV = (TextView)findViewById(R.id.sdtv);
        bnv = (BottomNavigationView) findViewById(R.id.bottombar);
        bnv.setSelectedItemId(R.id.miHome);
        pagetitle = (TextView)findViewById(R.id.PatientTitle);
        bnv.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener(){
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item){
                switch (item.getItemId())
                {
                    case (R.id.miHome):
                        consultation_to_home();
                        break;


                    case (R.id.miConsultation):
                        home_to_consultation();
                        break;
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
        ConstraintSet set  = new ConstraintSet();
        FragmentContainerView imv = (FragmentContainerView) findViewById(R.id.UpcomingConsultationContainer);
        ViewGroup.LayoutParams lp = (ConstraintLayout.LayoutParams)imv.getLayoutParams();
        ((ConstraintLayout.LayoutParams) lp).matchConstraintPercentHeight = 0f;
        pagetitle.setText("Consultations");
    }

    public void consultation_to_home()
    {
        ConstraintSet set  = new ConstraintSet();
        FragmentContainerView imv = (FragmentContainerView) findViewById(R.id.UpcomingConsultationContainer);
        ViewGroup.LayoutParams lp = (ConstraintLayout.LayoutParams)imv.getLayoutParams();
        ((ConstraintLayout.LayoutParams) lp).matchConstraintPercentHeight = 0.42f;
        pagetitle.setText("Home");
    }

    private void onAddButtonClicked(){
        setVisiblity(clicked);
        setAnimation(clicked);
        clicked = !clicked;
    }

    private void setVisiblity(boolean clicked){
        if(!clicked){
            newConsultationBtn.setVisibility(View.VISIBLE);
            searchDoctorBtn.setVisibility(View.VISIBLE);
            SDTV.setVisibility(View.VISIBLE);
            NCTV.setVisibility(View.VISIBLE);
            ConstraintLayout bnvbackground;
            bnvbackground = (ConstraintLayout) findViewById(R.id.constraintLayout);
            bnvbackground.setBackgroundResource(R.drawable.floatingbtngradient);
            bnv.setClickable(false);
            bnv.getChildAt(0).setEnabled(false);
        }
        else
        {
            newConsultationBtn.setVisibility(View.INVISIBLE);
            searchDoctorBtn.setVisibility(View.INVISIBLE);
            SDTV.setVisibility(View.INVISIBLE);
            NCTV.setVisibility(View.INVISIBLE);
            ConstraintLayout bnvbackground;
            bnvbackground = (ConstraintLayout) findViewById(R.id.constraintLayout);
            bnvbackground.setBackgroundResource(0);
            bnv.setClickable(true);
        }
    }

    private void setAnimation(boolean clicked){
        if(!clicked){
            newConsultationBtn.startAnimation(fromBottom);
            searchDoctorBtn.startAnimation(fromBottomleft);
            SDTV.setAnimation(fromBottomleft);
            NCTV.setAnimation(fromBottom);
            searchNewBtn.startAnimation(rotateOpen);

        }
        else
        {
            newConsultationBtn.startAnimation(toBottom);
            searchDoctorBtn.startAnimation(toBottomLeft);
            SDTV.setAnimation(toBottomLeft);
            NCTV.setAnimation(toBottom);
            searchNewBtn.startAnimation(rotateClose);
        }
    }

}