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


public class DoctorUpcomingConsultationsFragment extends Fragment {
    String patient_email, doc_email, doc_name, symptoms, consul_date, slot, date_id, slot_id, consultation_id="";

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference user_col = db.collection("users");

    String e_key="email";

    ArrayList<String> patients_name = new ArrayList<>();
    ArrayList<String> patients_email = new ArrayList<>();
    ArrayList<String> appointment_date_slot = new ArrayList<>();
    ArrayList<String> appointment_id = new ArrayList<>();


    DoctorUpcomingListAdapter dlv_adapter;
    ListView dlv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_doctor_upcoming_consultations, container, false);
        try {
            doc_email = getArguments().getString(e_key);
        }
        catch (Exception e)
        {
            doc_email = "";
        }
        dlv=(ListView) view.findViewById(R.id.DocupcomingConsultationsList);
        dlv_adapter = new DoctorUpcomingListAdapter(DoctorUpcomingConsultationsFragment.this, patients_name ,appointment_date_slot );

        user_col.document("user_"+doc_email).collection("Consultations").whereEqualTo("status_key","Booked").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

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

                    dlv_adapter.setdata(patients_name,appointment_date_slot);
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
                Intent upc_consul = new Intent(getContext(), DoctorUpcomingConsultationActivity.class);
                upc_consul.putExtra("doc_email", doc_email);
                upc_consul.putExtra("appointment_id", appointment_id.get(position));
                upc_consul.putExtra("patient_name", patients_name.get(position));
                upc_consul.putExtra("patient_email", patients_email.get(position));
                startActivity(upc_consul);
            }
        });


        View empty_view = view.findViewById(R.id.Docempty);
        dlv.setEmptyView(empty_view);
        dlv.setAdapter(dlv_adapter);
        return view;
    }
}