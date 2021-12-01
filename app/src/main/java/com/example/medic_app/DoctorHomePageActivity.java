package com.example.medic_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.ContactsContract;
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
import com.google.android.material.imageview.ShapeableImageView;

public class DoctorHomePageActivity extends AppCompatActivity {

    boolean doubleBackToExitPressedOnce = false;
    BottomNavigationView dbnv;
    TextView pagetitle;

    FloatingActionButton searchNewBtn;
    FloatingActionButton searchPatientBtn;
    FloatingActionButton newPatientBtn;
    TextView SPTV;
    TextView NPTV;
    boolean clicked = false;
    Animation rotateOpen;
    Animation rotateClose;
    Animation toBottom;
    Animation fromBottom;
    Animation toBottomLeft;
    Animation fromBottomleft;
    String email="";
    String e_key="email";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_home_page);

        try {
            Intent email_data = getIntent();
            email = email_data.getStringExtra(e_key);
        }catch (Exception e){e.printStackTrace();}

        final FragmentManager fragmentManager = getSupportFragmentManager();
        final Fragment fragment1 = new DoctorHomeFragment();
        final Fragment fragment2 = new DoctorSessionsFragment();
        NPTV = (TextView)findViewById(R.id.nptv);
        SPTV = (TextView)findViewById(R.id.sptv);
        dbnv = (BottomNavigationView) findViewById(R.id.doctorbottombar);
        dbnv.setSelectedItemId(R.id.miHome);
        rotateOpen = (Animation) AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_open);
        rotateClose = (Animation) AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_close);
        toBottom = (Animation) AnimationUtils.loadAnimation(getApplicationContext(),R.anim.to_bottom);
        fromBottom = (Animation) AnimationUtils.loadAnimation(getApplicationContext(),R.anim.from_bottom);
        toBottomLeft = (Animation)AnimationUtils.loadAnimation(getApplicationContext(),R.anim.to_bottom_left);
        fromBottomleft = (Animation)AnimationUtils.loadAnimation(getApplicationContext(),R.anim.from_bottom_left);

        searchNewBtn = (FloatingActionButton)findViewById(R.id.addbutton);
        searchNewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onAddButtonClicked();
            }
        });
        searchPatientBtn = (FloatingActionButton)findViewById(R.id.searchpatientbtn);
        searchPatientBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Search Patient!",Toast.LENGTH_LONG).show();
            }
        });
        newPatientBtn = (FloatingActionButton)findViewById(R.id.newpatientbtn);
        newPatientBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"New Patient!",Toast.LENGTH_LONG).show();

            }
        });

        dbnv.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener(){
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item){
                //Fragment fragment;
                switch (item.getItemId())
                {
                    case (R.id.miHome):
                        fragmentManager.beginTransaction().replace(R.id.DoctorHPContainer,fragment1).commit();
                        //fragment = fragment1;
                        break;


                    case (R.id.miSessions):
                        fragmentManager.beginTransaction().replace(R.id.DoctorHPContainer,fragment2).commit();
                        //fragment = fragment2;
                        //home_to_sessions();
                        break;
                }
                //fragmentManager.beginTransaction().replace(R.id.DoctorHPContainer,fragment).commit();
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

    private void onAddButtonClicked(){
        setVisiblity(clicked);
        setAnimation(clicked);
        clicked = !clicked;
    }

    private void setVisiblity(boolean clicked){
        if(!clicked){
            newPatientBtn.setVisibility(View.VISIBLE);
            searchPatientBtn.setVisibility(View.VISIBLE);
            SPTV.setVisibility(View.VISIBLE);
            NPTV.setVisibility(View.VISIBLE);
            ConstraintLayout bnvbackground;
            bnvbackground = (ConstraintLayout) findViewById(R.id.constraintLayout);
            bnvbackground.setBackgroundResource(R.drawable.floatingbtngradient);
            dbnv.setClickable(false);
            dbnv.getChildAt(0).setEnabled(false);
        }
        else
        {
            newPatientBtn.setVisibility(View.INVISIBLE);
            searchPatientBtn.setVisibility(View.INVISIBLE);
            SPTV.setVisibility(View.INVISIBLE);
            NPTV.setVisibility(View.INVISIBLE);
            ConstraintLayout bnvbackground;
            bnvbackground = (ConstraintLayout) findViewById(R.id.constraintLayout);
            bnvbackground.setBackgroundResource(0);
            dbnv.setClickable(true);
        }
    }

    private void setAnimation(boolean clicked){
        if(!clicked){
            newPatientBtn.startAnimation(fromBottomleft);
            searchPatientBtn.startAnimation(fromBottom);
            SPTV.setAnimation(fromBottom);
            NPTV.setAnimation(fromBottomleft);
            searchNewBtn.startAnimation(rotateOpen);

        }
        else
        {
            newPatientBtn.startAnimation(toBottomLeft);
            searchPatientBtn.startAnimation(toBottom);
            SPTV.setAnimation(toBottom);
            NPTV.setAnimation(toBottomLeft);
            searchNewBtn.startAnimation(rotateClose);
        }
    }
    public String retEkey(){
        return e_key;
    }
    public String retEmail(){
        return email;
    }
}