package com.example.medic_app;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class DoctorSessionsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_doctor_sessions, container, false);
        FragmentManager fm=getFragmentManager();
        FragmentTransaction ft= fm.beginTransaction();
        ft.replace(R.id.SessionsPreviewFragmentContainer,new DoctorDailyAppointmentsListFragment());
        ft.commit();
        return view;
    }
}