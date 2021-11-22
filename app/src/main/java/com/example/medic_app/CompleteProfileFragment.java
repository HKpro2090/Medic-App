package com.example.medic_app;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CompleteProfileFragment extends Fragment {
    public String patientGender,patientBloodgrp,patientAge;
    String[] bloodgroups = { "A+", "A-",
            "B+", "B-",
            "O+", "O-","AB-","AB+" };
    String[] gender = {"Male","Female","Prefer not to Say"};
    List<String> genderlist = Arrays.asList(gender);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_complete_profile, container, false);
        Spinner genderspinner = (Spinner) view.findViewById(R.id.GenderSpinner);
        ArrayAdapter<String> adp = new ArrayAdapter<String>(getActivity(),R.layout.support_simple_spinner_dropdown_item,gender);
        genderspinner.setAdapter(adp);
        genderspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                patientGender=gender[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        Spinner bgspinner = (Spinner) view.findViewById(R.id.BloodGroupSpinner);
        ArrayAdapter<String> ad = new ArrayAdapter<String>(getActivity(),R.layout.support_simple_spinner_dropdown_item,bloodgroups);
        bgspinner.setAdapter(ad);
        bgspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                patientBloodgrp=bloodgroups[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        Button cp=(Button) view.findViewById(R.id.CompleteProfileButton);
        EditText et=(EditText)view.findViewById(R.id.AgeCompleteProfile);
        cp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                patientAge=et.getText().toString();
                Toast.makeText(getActivity(),"Age:"+patientAge+"\nBloodGrp:"+patientBloodgrp+"\nGender:"+patientGender,Toast.LENGTH_LONG).show();
                Intent doctor_home = new Intent(getContext(), PatientHomePageActivity.class);     //Change Doctor Page Here
                startActivity(doctor_home);
            }
        });
        return view;
    }
}