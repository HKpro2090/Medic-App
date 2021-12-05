package com.example.medic_app;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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


public class PatientRecentConsultationsFragment extends Fragment {

    //Integer[] imgids={R.drawable.patient1,R.drawable.patient2,R.drawable.patient1};

    String patient_email, doc_email, doc_name, symptoms, consul_date, slot, date_id, slot_id, consultation_id="";
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference user_col = db.collection("users");

    String e_key="email";

    ArrayList<String> doctors_name = new ArrayList<>();
    ArrayList<String> doctor_email = new ArrayList<>();
    ArrayList<String> appointment_date_slot = new ArrayList<>();
    ArrayList<String> appointment_id = new ArrayList<>();
    ArrayList<Integer> imgid = new ArrayList<>();

    PatientRecentListAdapter lv_adapter;
    ListView lv;
    SharedPreferences shp;
    SharedPreferences.Editor ed;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_patient_recent_consultations, container, false);
        try {
            //patient_email = getArguments().getString(e_key);
            shp = getActivity().getSharedPreferences("sp", Context.MODE_PRIVATE);
            patient_email = shp.getString("username","");
        }
        catch (Exception e)
        {
            patient_email = "";
        }
        lv=(ListView) view.findViewById(R.id.RecentConsultationList);
        //Toast.makeText(getContext(), patient_email, Toast.LENGTH_SHORT).show();


        user_col.document("user_"+patient_email).collection("Consultations").whereEqualTo("status_key","Completed").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot document : task.getResult()) {
                        doctors_name.add(document.getString("doc_name_key"));
                        doctor_email.add(document.getString("doc_email_key"));
                        appointment_date_slot.add(document.getString("Appointment_date")+" "+document.getString("Slot_details_key"));
                        imgid.add(R.drawable.patient1);
                        appointment_id.add(document.getId());
                        lv_adapter = new PatientRecentListAdapter(PatientRecentConsultationsFragment.this,doctors_name,appointment_date_slot,imgid,patient_email,doctor_email);
                        lv_adapter.notifyDataSetChanged();
                        lv.setAdapter(lv_adapter);

                        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Intent con_rep = new Intent(getContext(),PatientConsultationReportActivity.class);
                                con_rep.putExtra("patient_email",patient_email);
                                con_rep.putExtra("appointment_id",appointment_id.get(position));
                                con_rep.putExtra("doc_name",doctors_name.get(position));
                                con_rep.putExtra("date_time_slot",appointment_date_slot.get(position));
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

        View empty_view = view.findViewById(R.id.empty2);
        lv.setEmptyView(empty_view);
        lv.setAdapter(lv_adapter);


        return view;
    }
}