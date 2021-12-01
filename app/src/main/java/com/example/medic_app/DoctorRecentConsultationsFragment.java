package com.example.medic_app;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;


public class DoctorRecentConsultationsFragment extends Fragment {
    String patient_email, doc_email, doc_name, symptoms, consul_date, slot, date_id, slot_id, consultation_id="";
//    FirebaseFirestore db = FirebaseFirestore.getInstance();
//    CollectionReference user_col = db.collection("users");

    String e_key="email";

    ArrayList<String> patients_name = new ArrayList<>();
    ArrayList<String> patients_email = new ArrayList<>();
    ArrayList<String> appointment_date_slot = new ArrayList<>();
    ArrayList<String> appointment_id = new ArrayList<>();
    ArrayList<String> doctor_email = new ArrayList<>();
    ArrayList<Integer> imgid = new ArrayList<>();

    PatientRecentListAdapter lv_adapter;
    ListView lv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_doctor_recent_consultations, container, false);
        try {
            patient_email = getArguments().getString(e_key);
        }
        catch (Exception e)
        {
            patient_email = "";
        }
        lv=(ListView) view.findViewById(R.id.DocRecentConsultationList);
        patients_name.add("Luke Skywalker");
        patients_name.add("Grogu");
        patients_name.add("Din Djarin");
        appointment_date_slot.add("1-12-2021 1:00pm");
        appointment_date_slot.add("2-12-2021 9:00am");
        appointment_date_slot.add("3-12-2021 4:00pm");
        imgid.add(R.drawable.patient1);
        imgid.add(R.drawable.patient2);
        imgid.add(R.drawable.patient1);
        lv_adapter = new PatientRecentListAdapter(DoctorRecentConsultationsFragment.this,patients_name,appointment_date_slot,imgid,patient_email,doctor_email);
        lv_adapter.notifyDataSetChanged();
        lv.setAdapter(lv_adapter);
//        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent con_rep = new Intent(getContext(),PatientConsultationReportActivity.class);
//                con_rep.putExtra("patient_email",patient_email);
//                con_rep.putExtra("appointment_id",appointment_id.get(position));
//                con_rep.putExtra("doc_name",patients_name.get(position));
//                con_rep.putExtra("date_time_slot",appointment_date_slot.get(position));
//                startActivity(con_rep);
//                //Toast.makeText(getContext(), "Clicked Item "+position, Toast.LENGTH_SHORT).show();
//            }
//        });
        View empty_view = view.findViewById(R.id.DocRempty);
        lv.setEmptyView(empty_view);
        lv.setAdapter(lv_adapter);
        return view;
    }
}