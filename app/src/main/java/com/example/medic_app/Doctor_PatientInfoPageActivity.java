package com.example.medic_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Doctor_PatientInfoPageActivity extends AppCompatActivity {

    String name,gender,bldgrp,doc_email,patient_email="";
    String ht,wt,age;
    TextView pname,pgender,pbgrp,pht,pwt,page;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference user_col = db.collection("users");

    ShapeableImageView dp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_patient_info_page);

        Intent in = getIntent();
        patient_email=in.getStringExtra("patient_email");
        name=in.getStringExtra("patient_name");
        doc_email=in.getStringExtra("doc_email");

        //placeholder values
//        name="ajitesh";
//        gender="male";
//        bldgrp="O+";
//        ht=180;
//        wt=80;
//        age=20;

        pname=(TextView)findViewById(R.id.PatientInfoName);
        page=(TextView)findViewById(R.id.pagenfo);
        pgender=(TextView)findViewById(R.id.pgenderinfo);
        pbgrp=(TextView)findViewById(R.id.pbginfo);
        pht=(TextView)findViewById(R.id.pheightinfo);
        pwt=(TextView)findViewById(R.id.pweifhtinfo);
        dp=(ShapeableImageView)findViewById(R.id.patientinfoimge);


        user_col.document("user_"+patient_email).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if(task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();

                    age = document.getString("age_key");
                    gender = document.getString("gender_key");
                    bldgrp = document.getString("blood_group_key");
                    ht = document.getString("height_key");
                    wt = document.getString("weight_key");

                    pname.setText(name);
                    page.setText(age);
                    pgender.setText(gender);
                    pbgrp.setText(bldgrp);
                    pht.setText(ht);
                    pwt.setText(wt);
                    dp.setImageResource(R.drawable.patient1);

                }else{
                    Toast.makeText(getApplicationContext(), "FireBase Error!", Toast.LENGTH_SHORT).show();
                }

            }
        });




//        FragmentManager fm=getSupportFragmentManager();
//        FragmentTransaction ft= fm.beginTransaction();
//        ft.replace(R.id.pinforecentconsultationview,new Doctor_PatientHistoryListFragment());
//        ft.commit();
    }
}