package com.example.medic_app;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;


public class DoctorDailyAppointmentsListFragment extends Fragment {

    ArrayList<String> appointment_slot_time = new ArrayList<>();
    ArrayList<String> ap_status = new ArrayList<>();
    PatientUpcomingListAdapter lv_adapter;
    ListView lv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_doctor_daily_appointments_list, container, false);
        appointment_slot_time.add("Slot 4 4:30pm");
        appointment_slot_time.add("Slot 1 9:30pm");
        appointment_slot_time.add("Slot 2 11:00pm");
        ap_status.add("Free");
        ap_status.add("Booked");
        ap_status.add("Booked");
        lv=(ListView) view.findViewById(R.id.DoctorDailyAppointments);
        lv_adapter = new PatientUpcomingListAdapter(this, appointment_slot_time, ap_status);

        lv.setAdapter(lv_adapter);

        return view;
    }
}