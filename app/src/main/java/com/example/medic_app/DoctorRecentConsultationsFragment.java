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


public class DoctorRecentConsultationsFragment extends Fragment {
    String patient_email, doc_email, doc_name, symptoms, consul_date, slot, date_id, slot_id, consultation_id="";

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference user_col = db.collection("users");

    String e_key="email";

    ArrayList<String> patients_name = new ArrayList<>();
    ArrayList<String> patients_email = new ArrayList<>();
    ArrayList<String> appointment_date_slot = new ArrayList<>();
    ArrayList<String> appointment_id = new ArrayList<>();
//    ArrayList<String> doctor_email = new ArrayList<>();
    Integer imgid = 0;

    DoctorRecentListAdapter dlv_adapter;
    ListView dlv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_doctor_recent_consultations, container, false);
        try {
            doc_email = getArguments().getString(e_key);
        }
        catch (Exception e)
        {
            doc_email = "";
        }
        dlv=(ListView) view.findViewById(R.id.DocRecentConsultationList);
        imgid = (R.drawable.patient1);
        dlv_adapter = new DoctorRecentListAdapter(this,patients_name,appointment_date_slot,imgid,patients_email,doc_email);

//        patients_name.add("Luke Skywalker");
//        patients_name.add("Grogu");
//        patients_name.add("Din Djarin");
//        appointment_date_slot.add("1-12-2021 1:00pm");
//        appointment_date_slot.add("2-12-2021 9:00am");
//        appointment_date_slot.add("3-12-2021 4:00pm");

//        imgid.add(R.drawable.patient2);
//        imgid.add(R.drawable.patient1);


//        dlv_adapter.notifyDataSetChanged();
//        dlv.setAdapter(dlv_adapter);
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

        user_col.document("user_"+doc_email).collection("Consultations").whereEqualTo("status_key","Completed").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (task.isSuccessful()) {
                    patients_name.clear();
                    patients_email.clear();
                    appointment_date_slot.clear();
                    appointment_id.clear();

                    for (QueryDocumentSnapshot document : task.getResult()) {
                        patients_name.add(document.getString("patient_name_key"));
                        patients_email.add(document.getString("patient_email_key"));
                        appointment_date_slot.add(document.getString("Appointment_date") + " " + document.getString("Slot_details_key"));
                        appointment_id.add(document.getId());
                    }

                    dlv_adapter.setdata(patients_name,appointment_date_slot,imgid,patients_email,doc_email);
                    dlv_adapter.notifyDataSetChanged();
                    dlv.setAdapter(dlv_adapter);

                } else {
                    Toast.makeText(getContext(), "Firebase Connection Error!", Toast.LENGTH_LONG).show();
                }
            }
        });

        dlv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent consul_report = new Intent(getContext(), DoctorConsultationReportActivity.class);
                consul_report.putExtra("doc_email", doc_email);
                consul_report.putExtra("appointment_id", appointment_id.get(position));
                consul_report.putExtra("patient_name", patients_name.get(position));
                consul_report.putExtra("patient_email", patients_email.get(position));
                consul_report.putExtra("date_time_slot",appointment_date_slot.get(position));
                startActivity(consul_report);
            }
        });

        View empty_view = view.findViewById(R.id.DocRempty);
        dlv.setEmptyView(empty_view);
        dlv.setAdapter(dlv_adapter);
        return view;
    }
}