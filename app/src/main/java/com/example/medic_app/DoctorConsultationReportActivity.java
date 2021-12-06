package com.example.medic_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class DoctorConsultationReportActivity extends AppCompatActivity {

    String doctor_email,patient_email,patient_name,date_time_slot,appointment_id,consultation_status,consultation_notes,prescription_meds="";
    TextView text_doc_name,text_date_time_slot,text_slot_status,text_consultation_notes,text_prescription_meds;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference user_col = db.collection("users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_consultation_report);

        text_doc_name = (TextView) findViewById(R.id.crpnametv);
        text_date_time_slot = (TextView) findViewById(R.id.crpdatetv);
        text_slot_status = (TextView) findViewById(R.id.crpstatustv);
        text_consultation_notes = (TextView) findViewById(R.id.patientnotes);
        text_prescription_meds = (TextView) findViewById(R.id.patientmedicines);

        //Make the textview scrollable
        text_consultation_notes.setMovementMethod(new ScrollingMovementMethod());

        Intent data_in = getIntent();
        doctor_email = data_in.getStringExtra("doc_email");
        patient_email = data_in.getStringExtra("patient_email");
        appointment_id = data_in.getStringExtra("appointment_id");
        patient_name = data_in.getStringExtra("patient_name");
        date_time_slot = data_in.getStringExtra("date_time_slot");

        user_col.document("user_"+doctor_email).collection("Consultations").document(appointment_id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();
                    if(document.exists()){

                        consultation_notes = document.getString("doctor_notes_key");
                        consultation_status = document.getString("status_key");
                        prescription_meds = document.getString("doctor_prescription_key");

                        if(consultation_notes.equals("")){
                            consultation_notes = "Notes / Diagnosis";
                        }
                        else{
                            //text_consultation_notes.setTextColor(Color.parseColor("#FFFFFF"));
                        }
                        if (prescription_meds.equals("")){
                            prescription_meds = "Medicines";
                        }
                        else{
                            //text_prescription_meds.setTextColor(Color.parseColor("#FFFFFF"));
                        }

                        text_doc_name.setText(patient_name);
                        text_date_time_slot.setText(date_time_slot);
                        text_slot_status.setText(consultation_status);
                        text_consultation_notes.setText(consultation_notes);
                        text_prescription_meds.setText(prescription_meds);

                    }else{
                        Toast.makeText(getApplicationContext(),"No such User or Consultation exists",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "FireBase Connection Error!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}