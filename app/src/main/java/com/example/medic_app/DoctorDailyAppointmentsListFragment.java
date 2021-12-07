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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;


public class DoctorDailyAppointmentsListFragment extends Fragment {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference user_col = db.collection("users");

    String[] slots = {"Slot1","Slot2","Slot3","Slot4","Slot5","Slot6","Slot7","Slot8",
            "Slot9","Slot10","Slot11","Slot12","Slot13","Slot14","Slot15","Slot16"};
    String[] slot_timings = {"09:00 to 09:30", "09:30 to 10:00","10:00 to 10:30","10:30 to 11:00","11:00 to 11:30",
            "11:30 to 12:00","12:00 to 12:30","12:30 to 13:00","14:00 to 14:30","14:30 to 15:00","15:00 to 15:30",
            "15:30 to 16:00", "16:00 to 16:30","16:30 to 17:00","17:00 to 17:30","17:30 to 18:00"};
    int slot_nos=16;
    String doctor_email,selected_date="";
    ArrayList<String> appointment_date_slot = new ArrayList<>();
    ArrayList<String> ap_status = new ArrayList<>();
    PatientUpcomingListAdapter lv_adapter;
    ListView lv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_doctor_daily_appointments_list, container, false);

        doctor_email = getArguments().getString("doc_email");
        selected_date = getArguments().getString("session_date");

        lv=(ListView) view.findViewById(R.id.DoctorDailyAppointments);

        lv_adapter = new PatientUpcomingListAdapter(this, appointment_date_slot, ap_status);
        Toast.makeText(getContext(),doctor_email+" " + selected_date,Toast.LENGTH_SHORT).show();
        user_col.document("user_"+doctor_email).collection("Sessions").document(selected_date).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if(task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();
                    for (int i=0;i<slot_nos;i++){
                        if(document.getString(slots[i])!=null){
                            appointment_date_slot.add("Slot "+(i+1)+" - "+slot_timings[i]);
                            ap_status.add(document.getString(slots[i]));
                        }
                    }

                    lv_adapter = new PatientUpcomingListAdapter(DoctorDailyAppointmentsListFragment.this, appointment_date_slot, ap_status);
                    lv_adapter.notifyDataSetChanged();
                    lv.setAdapter(lv_adapter);
                }else{

                    Toast.makeText(getContext(),"FireBase Error!",Toast.LENGTH_SHORT).show();
                }
            }
        });
//        appointment_date_slot.add("Slot 4 4:30pm");
//        appointment_date_slot.add("Slot 1 9:30pm");
//        appointment_date_slot.add("Slot 2 11:00pm");
//        ap_status.add("Free");
//        ap_status.add("Booked");
//        ap_status.add("Booked");
        View emptyview = view.findViewById(R.id.empty);
        lv.setEmptyView(emptyview);
        lv.setAdapter(lv_adapter);
        return view;
    }
}