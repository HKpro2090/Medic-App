package com.example.medic_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class DoctorPrescribeMedicineActivity extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference user_col = db.collection("users");

    EditText edit_notes,edit_medicines;
    String doctor_email,patient_email,appointment_id,notes,medicine="";
    Button add_prescription,cancel_prescription;

    String sessions_date,slot_key="";
    String split[] = new String[2];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_prescribe_medicine);

        Intent in = getIntent();
        doctor_email = in.getStringExtra("doc_email");
        patient_email = in.getStringExtra("patient_email");
        appointment_id = in.getStringExtra("appointment_id");

        edit_notes = (EditText) findViewById(R.id.Prescribenotes);
        edit_medicines = (EditText) findViewById(R.id.PrescribeMed);

        add_prescription = (Button) findViewById(R.id.AddPrescriptionButton);
        cancel_prescription = (Button) findViewById(R.id.CancelPrescriptionButton);

        cancel_prescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent doctor_home = new Intent(getApplicationContext(), DoctorHomePageActivity.class);
                doctor_home.putExtra("email", doctor_email);
                startActivity(doctor_home);
                finish();
            }
        });

        add_prescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notes = edit_notes.getText().toString();
                medicine = edit_notes.getText().toString();

                Map<String,Object> prescription = new HashMap<>();
                prescription.put("doctor_notes_key",notes);
                prescription.put("doctor_prescription_key",medicine);
                prescription.put("status_key","Completed");

                split = appointment_id.split("_");
                sessions_date = split[0];
                slot_key = split[1];

                if(notes.isEmpty()||medicine.isEmpty())
                {
                 Toast.makeText(getApplicationContext(),"Notes/Medicine cannot be Empty!",Toast.LENGTH_SHORT).show();
                 edit_notes.setError("");
                 edit_medicines.setError("");
                }else {
                    user_col.document("user_" + doctor_email).collection("Consultations").document(appointment_id).set(prescription, SetOptions.merge());
                    user_col.document("user_" + patient_email).collection("Consultations").document(appointment_id).set(prescription, SetOptions.merge());
                    user_col.document("user_" + doctor_email).collection("Sessions").document(sessions_date).update(slot_key, "Completed").addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), "Prescription added successfully!", Toast.LENGTH_SHORT).show();
                                Intent doctor_home = new Intent(getApplicationContext(), DoctorHomePageActivity.class);
                                doctor_home.putExtra("email", doctor_email);
                                startActivity(doctor_home);
                                finish();
                            } else {
                                Toast.makeText(getApplicationContext(), "FireBase Connection Error!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

    }
}