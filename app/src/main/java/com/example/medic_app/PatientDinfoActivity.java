package com.example.medic_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class PatientDinfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_dinfo);
        ImageView im=(ImageView)findViewById(R.id.doctorinfoimge);
        im.setImageResource(R.drawable.patient1);
        String name="Dr.abc";
        String qual="MBBS";
        String dep="ENT";
        String ab="two line string ";
        TextView nam=(TextView)findViewById(R.id.dinfonametv);
        TextView qua=(TextView)findViewById(R.id.dinfoqualtv);
        TextView de=(TextView)findViewById(R.id.dinfodeptv);
        TextView abou=(TextView)findViewById(R.id.dinfoabouttv);
        nam.setText(name);
        qua.setText(qual);
        de.setText(dep);
        abou.setText(ab);

    }
}