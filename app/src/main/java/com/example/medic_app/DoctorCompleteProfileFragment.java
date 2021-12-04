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


public class DoctorCompleteProfileFragment extends Fragment {
    public String Dage, Dqual, Dabout,Dgender,Ddept;
    String [] gender={"Male","Female","Prefer not to Say"};
    String [] dept={"Orthopaedic","ENT","Cardio","Pediatrician","General Physician"};
    String email="";
    String e_key="email";
    List<String> genderlist = Arrays.asList(gender);
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference user_col = db.collection("users");
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_doctor_complete_profile, container, false);

        Bundle email_bundle = getArguments();
        try {
            email = email_bundle.getString(e_key);
        }
        catch (Exception e){
            email="";
        }
        Spinner DgenSpinner=(Spinner)view.findViewById(R.id.DoctorGenderSpinner);
        Spinner DdepSpinner=(Spinner)view.findViewById(R.id.DoctorDeptSpinner);

        ArrayAdapter<String> adp = new ArrayAdapter<String>(getActivity(),R.layout.my_selected_item,gender);
        DgenSpinner.setAdapter(adp);
        DgenSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Dgender=gender[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<String> ad = new ArrayAdapter<String>(getActivity(),R.layout.my_selected_item,dept);
        DdepSpinner.setAdapter(ad);
        DdepSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Ddept=dept[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Button Dcpbtn=(Button) view.findViewById(R.id.DoctorCompleteProfileButton);
        EditText Da=(EditText)view.findViewById(R.id.DoctorAgeCompleteProfile);
        EditText Dq=(EditText)view.findViewById(R.id.DoctorQualification);
        EditText Dab=(EditText)view.findViewById(R.id.DoctorAbout);
        Dcpbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dage=Da.getText().toString();
                Dqual=Dq.getText().toString();
                Dabout=Dab.getText().toString();

                Map<String,Object> user_sign_up_additional_details = new Hashtable<>();
                user_sign_up_additional_details.put("age_key",Dage);
                user_sign_up_additional_details.put("Qualification_key",Dqual);
                user_sign_up_additional_details.put("gender_key",Dgender);
                user_sign_up_additional_details.put("about_key",Dabout);
                user_sign_up_additional_details.put("department_key",Ddept);

                user_col.document("user_"+email).set(user_sign_up_additional_details, SetOptions.merge()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(getActivity(), "User Details Updated Successfully!\n"+Dage+Dqual+Dgender+Dabout+Ddept, Toast.LENGTH_LONG).show();
                        Intent doctor_home = new Intent(getContext(), DoctorHomePageActivity.class);
                        doctor_home.putExtra("email", email);
                        startActivity(doctor_home);
                        getActivity().finish();
                    }
                });

            }
        });

        return view;
    }
}