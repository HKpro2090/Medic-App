package com.example.medic_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;

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
        } catch (Exception e) {
            e.printStackTrace();
        }


        NPTV = (TextView) findViewById(R.id.nptv);
        SPTV = (TextView) findViewById(R.id.sptv);
        dbnv = (BottomNavigationView) findViewById(R.id.doctorbottombar);
        load_doc_sessions_frag(new DoctorHomeFragment());

        dbnv.setSelectedItemId(R.id.miHome);
        rotateOpen = (Animation) AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_open);
        rotateClose = (Animation) AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_close);
        toBottom = (Animation) AnimationUtils.loadAnimation(getApplicationContext(), R.anim.to_bottom);
        fromBottom = (Animation) AnimationUtils.loadAnimation(getApplicationContext(), R.anim.from_bottom);
        toBottomLeft = (Animation) AnimationUtils.loadAnimation(getApplicationContext(), R.anim.to_bottom_left);
        fromBottomleft = (Animation) AnimationUtils.loadAnimation(getApplicationContext(), R.anim.from_bottom_left);

        searchNewBtn = (FloatingActionButton) findViewById(R.id.addbutton);
        searchNewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onAddButtonClicked();
            }
        });
        searchPatientBtn = (FloatingActionButton) findViewById(R.id.searchpatientbtn);
        searchPatientBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Search Patient!", Toast.LENGTH_LONG).show();
                Intent i = new Intent(getApplicationContext(), DoctorSearchPatientActivity.class);
                i.putExtra(e_key, email);
                startActivity(i);
            }
        });
        newPatientBtn = (FloatingActionButton) findViewById(R.id.newsessionbtn);
        newPatientBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "New Session!", Toast.LENGTH_LONG).show();
                Intent i = new Intent(getApplicationContext(), DoctorNewSessionActivity.class);
                i.putExtra(e_key, email);
                startActivity(i);
            }
        });


        dbnv.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case (R.id.miHome):
                        load_doc_sessions_frag(new DoctorHomeFragment());
                        break;


                    case (R.id.miSessions):
                        load_doc_sessions_frag(new DoctorSessionsFragment());
                        break;
                }
                return true;
            }
        });

        SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.refreshLayout2);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Toast.makeText(getApplicationContext(),"Refreshed",Toast.LENGTH_LONG).show();
                swipeRefreshLayout.setRefreshing(false);
                load_doc_sessions_frag(new DoctorHomeFragment());
            }
        });

    }

//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item){
//                //Fragment fragment;
//
//
//                return true;
//            }
//        });
//    }

    public void load_doc_sessions_frag(Fragment fragment){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.setCustomAnimations(R.anim.fade_in,R.anim.fade_out);
        Bundle email_trans = new Bundle();
        email_trans.putString(e_key,email);
        fragment.setArguments(email_trans);
        //Toast.makeText(getApplicationContext(),"Inside load Fragment:"+email,Toast.LENGTH_LONG).show();
        ft.replace(R.id.DoctorHPContainer,fragment);
        ft.setReorderingAllowed(true).commit();
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