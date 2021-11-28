package com.example.medic_app;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class PatientCompleteProfileFragment extends Fragment {

    public String patientGender,patientBloodgrp,patientAge,patientHeight,patientWeight;
    String email="";
    String e_key="email";
    String[] bloodgroups = { "A+", "A-",
            "B+", "B-",
            "O+", "O-","AB-","AB+" };
    String[] gender = {"Male","Female","Prefer not to Say"};
    List<String> genderlist = Arrays.asList(gender);
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference user_col = db.collection("users");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Bundle email_bundle = getArguments();
        try {
            email = email_bundle.getString(e_key);
        }catch(Exception err){
            email="";
        }

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
        EditText ht = (EditText)view.findViewById(R.id.heightCompleteProfile);
        EditText wt = (EditText)view.findViewById(R.id.WeightCompleteProfile);
        cp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                patientAge=et.getText().toString();
                patientHeight=ht.getText().toString();
                patientWeight=wt.getText().toString();

                Map<String,Object> user_sign_up_additional_details = new Hashtable<>();
                user_sign_up_additional_details.put("age_key",patientAge);
                user_sign_up_additional_details.put("height_key",patientHeight);
                user_sign_up_additional_details.put("weight_key",patientWeight);
                user_sign_up_additional_details.put("blood_group_key",patientBloodgrp);
                user_sign_up_additional_details.put("gender_key",patientGender);

                user_col.document("user_"+email).set(user_sign_up_additional_details, SetOptions.merge()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(getActivity(), "User Details Updated Successfully!", Toast.LENGTH_SHORT).show();
                       Intent patient_home = new Intent(getContext(), PatientHomePageActivity.class);
                       patient_home.putExtra("email", email);
                       startActivity(patient_home);
                       getActivity().finish();
                    }
                });


            }
        });
        return view;
    }
}