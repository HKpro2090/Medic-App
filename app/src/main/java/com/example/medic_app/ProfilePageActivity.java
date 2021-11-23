package com.example.medic_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.TextView;

public class ProfilePageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);
        TextView logout=(TextView) findViewById(R.id.textview9);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(in);
                finishAffinity();
            }
        });

        TextView changepass=(TextView) findViewById(R.id.textview8);
        changepass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(getApplicationContext(),MainActivity.class);
                in.putExtra("changepass","yes");
                startActivity(in);

            }
        });
    }
}