package com.example.medic_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.imageview.ShapeableImageView;

public class Doctor_PatientInfoPageActivity extends AppCompatActivity {
    String name,gender,bldgrp,email;
    int ht,wt,age;
    TextView pname,pgender,pbgrp,pht,pwt,page;

    ShapeableImageView dp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_patient_info_page);

        //placeholder values
        name="ajitesh";
        gender="male";
        bldgrp="O+";
        ht=180;
        wt=80;
        age=20;

        pname=(TextView)findViewById(R.id.PatientInfoName);
        page=(TextView)findViewById(R.id.pagenfo);
        pgender=(TextView)findViewById(R.id.pgenderinfo);
        pbgrp=(TextView)findViewById(R.id.pbginfo);
        pht=(TextView)findViewById(R.id.pheightinfo);
        pwt=(TextView)findViewById(R.id.pweifhtinfo);
        dp=(ShapeableImageView)findViewById(R.id.patientinfoimge);
        pname.setText(name);
        page.setText(Integer.toString(age));
        pgender.setText(gender);
        pbgrp.setText(bldgrp);
        pht.setText(Integer.toString(ht));
        pwt.setText(Integer.toString(wt));
        dp.setImageResource(R.drawable.patient1);

        FragmentManager fm=getSupportFragmentManager();
        FragmentTransaction ft= fm.beginTransaction();
        ft.replace(R.id.pinforecentconsultationview,new Doctor_PatientHistoryListFragment());
        ft.commit();
    }
}