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


public class DoctorUpcomingConsultationsFragment extends Fragment {
    String patient_email, doc_email, doc_name, symptoms, consul_date, slot, date_id, slot_id, consultation_id="";
//    FirebaseFirestore db = FirebaseFirestore.getInstance();
//    CollectionReference user_col = db.collection("users");

    String e_key="email";
    ArrayList<String> patients_name = new ArrayList<>();
    ArrayList<String> patients_email = new ArrayList<>();
    ArrayList<String> appointment_date_slot = new ArrayList<>();
    ArrayList<String> appointment_id = new ArrayList<>();
    PatientUpcomingListAdapter lv_adapter;
    ListView lv;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_doctor_upcoming_consultations, container, false);
        try {
            patient_email = getArguments().getString(e_key);
        }
        catch (Exception e)
        {
            patient_email = "";
        }
        lv=(ListView) view.findViewById(R.id.DocupcomingConsultationsList);

        //placeholder values
        patients_name.add("Ajitesh");
        patients_name.add("Hari");
        patients_name.add("Gautam");
        appointment_date_slot.add("2-12-2021 12:00pm");
        appointment_date_slot.add("5-12-2021 10:00am");
        appointment_date_slot.add("7-12-2021 2:00pm");

        lv_adapter = new PatientUpcomingListAdapter(DoctorUpcomingConsultationsFragment.this,patients_name ,appointment_date_slot );
        lv_adapter.notifyDataSetChanged();
        lv.setAdapter(lv_adapter);
//        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent upc_consul = new Intent(getContext(), Patient_upcoming_consultation_activity.class);
//                upc_consul.putExtra("patient_email", patient_email);
//                upc_consul.putExtra("appointment_id", appointment_id.get(position));
//                upc_consul.putExtra("doc_name", patients_name.get(position));
//                upc_consul.putExtra("doc_email", patients_email.get(position));
//                startActivity(upc_consul);
//                //Toast.makeText(getContext(), "Clicked Item "+position, Toast.LENGTH_SHORT).show();
//            }
//        });
        View empty_view = view.findViewById(R.id.Docempty);
        lv.setEmptyView(empty_view);
        lv.setAdapter(lv_adapter);
        return view;
    }
}