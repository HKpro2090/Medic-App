package com.example.medic_app;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;


public class PatientRecentConsultationsFragment extends Fragment {
    String[] upcomingdatesrc={"07-11-2021 720pm","08-11-2021 720pm","09-11-2021 720pm"};
    String[] doctors={"Dr.abc","Dr.pqr","Dr.xyz"};
    Integer[] imgid={R.drawable.patient1,R.drawable.patient2,R.drawable.patient1};
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_patient_recent_consultations, container, false);
        ListView lv=(ListView) view.findViewById(R.id.RecentConsultationList);
        PatientRecentListAdapter ad=new PatientRecentListAdapter(this,doctors,upcomingdatesrc,imgid);
        lv.setAdapter(ad);
        return view;
    }
}