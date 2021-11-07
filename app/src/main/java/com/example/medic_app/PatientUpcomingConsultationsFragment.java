package com.example.medic_app;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class PatientUpcomingConsultationsFragment extends Fragment {
    String[] upcomingdates={"07-11-2021 720pm","08-11-2021 720pm","09-11-2021 720pm"};
    String[] doctors={"Dr.abc","Dr.pqr","Dr.xyz"};
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_patient_upcoming_consultations, container, false);
        ListView lv=(ListView) view.findViewById(R.id.upcomingConsultationsList);
        PatientUpcomingListAdapter ad=new PatientUpcomingListAdapter(this,upcomingdates,doctors);
        lv.setAdapter(ad);

        return view;
    }
}