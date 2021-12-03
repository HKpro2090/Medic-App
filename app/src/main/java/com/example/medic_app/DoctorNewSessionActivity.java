package com.example.medic_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class DoctorNewSessionActivity extends AppCompatActivity {
    Button cancelbtn,bookbtn;
    DoctorNewSessionBookSlotsListViewFragment frag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_new_session);
        cancelbtn=(Button) findViewById(R.id.nscancelbutton);
        bookbtn=(Button)findViewById(R.id.nsbookbutton);
        cancelbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        frag=new DoctorNewSessionBookSlotsListViewFragment();
        FragmentManager fm=getSupportFragmentManager();
        FragmentTransaction ft=fm.beginTransaction();
        ft.replace(R.id.SessionsSelectFragmentContainer,frag);
        ft.commit();
        bookbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> bookedslots=frag.bookedSlots;
                String msg="";
                for(int i=0;i<bookedslots.size();i++){
                    msg=msg+"\n"+bookedslots.get(i);
                }
                Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_LONG).show();
            }
        });


    }
}