package com.example.medic_app;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.imageview.ShapeableImageView;


public class DoctorHomeFragment extends Fragment {
    public DoctorHomePageActivity dha;
    public String e_key;
    public String email;
    ShapeableImageView DocDP;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_doctor_home, container, false);
        DocDP = (ShapeableImageView)view.findViewById(R.id.DoctorHProfilePic);
        DocDP.setImageResource(R.drawable.patient1);
        DocDP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent doctor_settings_page = new Intent(getActivity(), SettingsPageActivity.class);
                doctor_settings_page.putExtra(e_key, email);
                startActivity(doctor_settings_page);
            }
            });

        loadDocUpConsulFragment(new PatientUpcomingConsultationsFragment());
        loadDocRecentConsulFragment (new PatientRecentConsultationsFragment());
        return view;
    }
    public void loadDocUpConsulFragment(Fragment fragment)
    {
        dha=(DoctorHomePageActivity) getActivity();
        e_key= dha.e_key;
        email=dha.email;
        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        Bundle email_out = new Bundle();
        email_out.putString(e_key,email);
        fragment.setArguments(email_out);
        fragmentTransaction.replace(R.id.DoctorUpcomingConsultationContainer, fragment);
        fragmentTransaction.setReorderingAllowed(true).commit();
    }
    public void loadDocRecentConsulFragment(Fragment fragment)
    {
        dha=(DoctorHomePageActivity) getActivity();
        e_key= dha.e_key;
        email=dha.email;
        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        Bundle email_out = new Bundle();
        email_out.putString(e_key,email);
        fragment.setArguments(email_out);
        fragmentTransaction.replace(R.id.DoctorRecentConsultationContainer, fragment);
        fragmentTransaction.setReorderingAllowed(true).commit();
    }

}