package com.example.medic_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class DoctorUpcomingConsultationActivity extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference user_col = db.collection("users");

    TextView text_patient_name,text_date,text_slot,text_slot_status,text_consultation_symptoms;
    Button add_prescription;
    ImageView dp;
    String doctor_email,patient_email,appointment_id,patient_name;
    String consultation_symptoms,consultation_status,consultation_date,slot_details;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_upcoming_consultation);

        text_patient_name = (TextView) findViewById(R.id.upcpnametv);
        text_date = (TextView) findViewById(R.id.upcpdatetv);
        text_slot = (TextView) findViewById(R.id.upcptimetv);
        text_slot_status = (TextView) findViewById(R.id.upcpstatustv);
        text_consultation_symptoms = (TextView) findViewById(R.id.upcpsymtoms);

        add_prescription = (Button) findViewById(R.id.PrescribeMedicineButton);
        dp=(ImageView)findViewById(R.id.upcpphoto);
        dp.setImageResource(R.drawable.patient1);
        Intent data_in = getIntent();
        patient_email = data_in.getStringExtra("patient_email");
        doctor_email = data_in.getStringExtra("doc_email");
        appointment_id = data_in.getStringExtra("appointment_id");
        patient_name = data_in.getStringExtra("patient_name");

        user_col.document("user_"+doctor_email).collection("Consultations").document(appointment_id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();
                    if(document.exists()){

                        consultation_symptoms = document.getString("symptoms_key");
                        consultation_status = document.getString("status_key");
                        consultation_date = document.getString("Appointment_date");
                        slot_details = document.getString("Slot_details_key");

                        text_patient_name.setText(patient_name);
                        text_date.setText(consultation_date);
                        text_slot.setText(slot_details);
                        text_slot_status.setText(consultation_status);

                        if(consultation_symptoms.equals("")) {
                            text_consultation_symptoms.setText("Symptoms");
                            text_consultation_symptoms.setText(consultation_symptoms);
                        }
                        else{
                            //text_consultation_symptoms.setTextColor(Color.parseColor("#FFFFFF"));
                            text_consultation_symptoms.setText(consultation_symptoms);
                        }

                    }else{
                        Toast.makeText(getApplicationContext(),"No such User or Consultation exists",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "FireBase Connection Error!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        add_prescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent add_pres = new Intent(getApplicationContext(), DoctorPrescribeMedicineActivity.class);
                add_pres.putExtra("doc_email", doctor_email);
                add_pres.putExtra("appointment_id", appointment_id);
                add_pres.putExtra("patient_email", patient_email);
                startActivity(add_pres);
            }
        });
    }
}