package com.example.medic_app;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class PatientUpcomingConsultationsFragment extends Fragment {

    String patient_email, doc_email, doc_name, symptoms, consul_date, slot, date_id, slot_id, consultation_id="";
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference user_col = db.collection("users");

    String e_key="email";
    ArrayList<String> doctors_name = new ArrayList<>();
    ArrayList<String> doctor_email = new ArrayList<>();
    ArrayList<String> appointment_date_slot = new ArrayList<>();
    ArrayList<String> appointment_id = new ArrayList<>();
    PatientUpcomingListAdapter lv_adapter;
    ListView lv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view=inflater.inflate(R.layout.fragment_patient_upcoming_consultations, container, false);

        patient_email = getArguments().getString(e_key);
        lv=(ListView) view.findViewById(R.id.upcomingConsultationsList);
        //Toast.makeText(getContext(), patient_email, Toast.LENGTH_SHORT).show();


        user_col.document("user_"+patient_email).collection("Consultations").whereEqualTo("status_key","Booked").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot document : task.getResult()) {
                        doctors_name.add(document.getString("doc_name_key"));
                        doctor_email.add(document.getString("doc_email_key"));
                        appointment_date_slot.add(document.getString("Appointment_date")+" "+document.getString("Slot_details_key"));
                        appointment_id.add(document.getId());
                    }

                    lv_adapter = new PatientUpcomingListAdapter(PatientUpcomingConsultationsFragment.this,doctors_name,appointment_date_slot);
                    lv_adapter.notifyDataSetChanged();
                    lv.setAdapter(lv_adapter);
                }else{
                    Toast.makeText(getContext(),"Firebase Connection Error!",Toast.LENGTH_LONG).show();
                }
            }
        });

        View empty_view = view.findViewById(R.id.empty);
        lv.setEmptyView(empty_view);
        lv.setAdapter(lv_adapter);

        return view;
    }

}
