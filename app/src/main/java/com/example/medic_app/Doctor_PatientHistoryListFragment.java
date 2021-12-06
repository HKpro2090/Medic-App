package com.example.medic_app;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class Doctor_PatientHistoryListFragment extends Fragment {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference user_col = db.collection("users");

    String patient_email,patient_name="";
    ArrayList<String> appointment_date_time = new ArrayList<>();
    ArrayList<String> ap_symptoms = new ArrayList<>();
    ArrayList<String> appointment_id = new ArrayList<>();
    PatientUpcomingListAdapter lv_adapter;
    ListView lv;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_doctor__patient_history_list, container, false);
        lv=(ListView) view.findViewById(R.id.DoctorPAppointmentHistory);
        lv_adapter = new PatientUpcomingListAdapter(this, appointment_date_time, ap_symptoms);

        patient_email = getArguments().getString("patient_email");
        patient_name = getArguments().getString("patient_name");

        user_col.document("user_"+patient_email).collection("Consultations").whereEqualTo("status_key","Completed").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot document : task.getResult()) {
                        ap_symptoms.add(document.getString("symptoms_key"));
                        appointment_date_time.add(document.getString("Appointment_date")+" "+document.getString("Slot_details_key"));
                        appointment_id.add(document.getId());
                        lv_adapter = new PatientUpcomingListAdapter(Doctor_PatientHistoryListFragment.this,appointment_date_time,ap_symptoms);
                        lv_adapter.notifyDataSetChanged();
                        lv.setAdapter(lv_adapter);

                        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Intent con_rep = new Intent(getContext(),DoctorConsultationReportActivity.class);
                                con_rep.putExtra("patient_email",patient_email);
                                con_rep.putExtra("appointment_id",appointment_id.get(position));
                                con_rep.putExtra("patient_name",patient_name);
                                con_rep.putExtra("date_time_slot",appointment_date_time.get(position));
                                startActivity(con_rep);

                                //Toast.makeText(getContext(), "Clicked Item "+position, Toast.LENGTH_SHORT).show();
                            }
                        });
                    }




                }else{
                    Toast.makeText(getContext(),"Firebase Connection Error!",Toast.LENGTH_LONG).show();
                }
            }
        });

//        patient_email = getArguments().getString("patient_email");
//        appointment_date_time.add("2-12-21 4:30pm");
//        appointment_date_time.add("3-10-21 9:30pm");
//        appointment_date_time.add("27-10-21 11:00pm");
//        ap_symptoms.add("Headache");
//        ap_symptoms.add("-");
//        ap_symptoms.add("joint pain");
//        lv=(ListView) view.findViewById(R.id.DoctorPAppointmentHistory);
//        lv_adapter = new PatientUpcomingListAdapter(this, appointment_date_time, ap_symptoms);

        lv.setAdapter(lv_adapter);
        return view;
    }
}