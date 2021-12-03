package com.example.medic_app;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

public class Doctor_PatientHistoryListFragment extends Fragment {

    ArrayList<String> appointment_date_time = new ArrayList<>();
    ArrayList<String> ap_symptoms = new ArrayList<>();
    PatientUpcomingListAdapter lv_adapter;
    ListView lv;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_doctor__patient_history_list, container, false);
        appointment_date_time.add("2-12-21 4:30pm");
        appointment_date_time.add("3-10-21 9:30pm");
        appointment_date_time.add("27-10-21 11:00pm");
        ap_symptoms.add("Headache");
        ap_symptoms.add("-");
        ap_symptoms.add("joint pain");
        lv=(ListView) view.findViewById(R.id.DoctorPAppointmentHistory);
        lv_adapter = new PatientUpcomingListAdapter(this, appointment_date_time, ap_symptoms);
        lv.setAdapter(lv_adapter);
        return view;
    }
}