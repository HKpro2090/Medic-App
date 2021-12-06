package com.example.medic_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
    String email="";
    String e_key="email";
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
    Animation fadein;
    Animation fadeout;

    SharedPreferences shp;
    SharedPreferences.Editor ed;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Intent email_data  = getIntent();
        //email = email_data.getStringExtra(e_key);
        shp = getSharedPreferences("sp", Context.MODE_PRIVATE);
        email = shp.getString("username","");
        setContentView(R.layout.activity_home_page);
        ImageView im=(ImageView)findViewById(R.id.PatientProfilePic);
        im.setImageResource(R.drawable.patient1);


        loadPatUpConsulFragment(new PatientUpcomingConsultationsFragment());
        loadPatRecentConsulFragment (new PatientRecentConsultationsFragment());


        im.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent patient_settings_page = new Intent(getApplicationContext(), SettingsPageActivity.class);
                //patient_settings_page.putExtra(e_key,email);
                startActivity(patient_settings_page);
            }
        });
        //Code related to bottom navigation bar
        rotateOpen = (Animation) AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_open);
        rotateClose = (Animation) AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_close);
        toBottom = (Animation) AnimationUtils.loadAnimation(getApplicationContext(),R.anim.to_bottom);
        fromBottom = (Animation) AnimationUtils.loadAnimation(getApplicationContext(),R.anim.from_bottom);
        toBottomLeft = (Animation)AnimationUtils.loadAnimation(getApplicationContext(),R.anim.to_bottom_left);
        fromBottomleft = (Animation)AnimationUtils.loadAnimation(getApplicationContext(),R.anim.from_bottom_left);
        fadein = (Animation)AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_in);
        fadeout = (Animation) AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_out);
//        FloatingActionButton b=(FloatingActionButton)findViewById(R.id.addbutton);
//        b.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent in=new Intent(getApplicationContext(),NewConsultationActivity.class);
//                //in.putExtra(e_key,email);
//                startActivity(in);
//            }
//        });

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
                Intent i=new Intent(getApplicationContext(),PatientDsearchActivity.class);
                //i.putExtra(e_key,email);
                startActivity(i);
            }
        });
        newConsultationBtn = (FloatingActionButton)findViewById(R.id.newconsultationbtn);
        newConsultationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in=new Intent(getApplicationContext(),NewConsultationActivity.class);
                //in.putExtra(e_key,email);
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

        SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.refreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Toast.makeText(getApplicationContext(),"Refreshed",Toast.LENGTH_LONG).show();
                swipeRefreshLayout.setRefreshing(false);
                loadPatUpConsulFragment(new PatientUpcomingConsultationsFragment());
                loadPatRecentConsulFragment (new PatientRecentConsultationsFragment());
            }
        });

    }

    public void loadPatUpConsulFragment(Fragment fragment)
    {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        Bundle email_out = new Bundle();
        email_out.putString(e_key,email);
        fragment.setArguments(email_out);
        fragmentTransaction.replace(R.id.UpcomingConsultationContainer, fragment);
        fragmentTransaction.setReorderingAllowed(true).commit();
    }

    public void loadPatRecentConsulFragment(Fragment fragment)
    {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        Bundle email_out = new Bundle();
        email_out.putString(e_key,email);
        fragment.setArguments(email_out);
        fragmentTransaction.replace(R.id.RecentConsultationContainer, fragment);
        fragmentTransaction.setReorderingAllowed(true).commit();
    }

    //Code to press back twice to exit.
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            finishAffinity();
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
        PatientRecentConsultationsFragment.homeornot = false;
        loadPatRecentConsulFragment(new PatientRecentConsultationsFragment());
        ConstraintSet set  = new ConstraintSet();
        FragmentContainerView imv = (FragmentContainerView) findViewById(R.id.UpcomingConsultationContainer);
        FragmentContainerView imv2 = (FragmentContainerView)findViewById(R.id.RecentConsultationContainer);
        //TextView tv=(TextView)findViewById(R.id.textView);
        //tv.setVisibility(View.INVISIBLE);
        ViewGroup.LayoutParams lp = (ConstraintLayout.LayoutParams)imv.getLayoutParams();
        ((ConstraintLayout.LayoutParams) lp).matchConstraintPercentHeight = 0f;
        pagetitle.setText("Consultations");
        pagetitle.startAnimation(fadeout);
        pagetitle.startAnimation(fadein);
        imv.startAnimation(fadeout);
        imv.startAnimation(fadein);
        imv2.startAnimation(fadeout);
        imv2.startAnimation(fadein);
        //TextView tv=(TextView)findViewById(R.id.textView);
        //tv.setVisibility(View.INVISIBLE);

    }

    public void consultation_to_home()
    {
        PatientRecentConsultationsFragment.homeornot = true;
        loadPatRecentConsulFragment(new PatientRecentConsultationsFragment());
        ConstraintSet set  = new ConstraintSet();
        FragmentContainerView imv = (FragmentContainerView) findViewById(R.id.UpcomingConsultationContainer);
        FragmentContainerView imv2 = (FragmentContainerView)findViewById(R.id.RecentConsultationContainer);
        ViewGroup.LayoutParams lp = (ConstraintLayout.LayoutParams)imv.getLayoutParams();
        ((ConstraintLayout.LayoutParams) lp).matchConstraintPercentHeight = 0.42f;
        pagetitle.setText("Home");
        //TextView tv=(TextView)findViewById(R.id.textView);
        //tv.setVisibility(View.VISIBLE);
        pagetitle.startAnimation(fadeout);
        pagetitle.startAnimation(fadein);
        imv.startAnimation(fadeout);
        imv.startAnimation(fadein);
        imv2.startAnimation(fadeout);
        imv2.startAnimation(fadein);
        PatientRecentConsultationsFragment.homeornot = true;
        loadPatRecentConsulFragment(new PatientRecentConsultationsFragment());
        //TextView tv=(TextView)findViewById(R.id.textView);
        //tv.setVisibility(View.VISIBLE);
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