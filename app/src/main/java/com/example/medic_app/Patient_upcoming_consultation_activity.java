package com.example.medic_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Patient_upcoming_consultation_activity extends AppCompatActivity {

    String patient_email,doctor_email,doctor_name,consultation_date,slot_details,consultation_status,appointment_id,consultation_symptoms="";
    TextView text_doc_name,text_date,text_slot,text_slot_status,text_consultation_symptoms;
    Button cancel_appointment;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference user_col = db.collection("users");

    String sessions_date,slot_key="";
    String split[] = new String[2];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_upcoming_consultation);


        text_doc_name = (TextView) findViewById(R.id.upcdnametv);
        text_date = (TextView) findViewById(R.id.upcdatetv);
        text_slot = (TextView) findViewById(R.id.upctimetv);
        text_slot_status = (TextView) findViewById(R.id.upcstatustv);
        text_consultation_symptoms = (TextView) findViewById(R.id.upcsymtoms);

        cancel_appointment = (Button) findViewById(R.id.CancelConsultationButton);

        Intent data_in = getIntent();
        patient_email = data_in.getStringExtra("patient_email");
        doctor_email = data_in.getStringExtra("doc_email");
        appointment_id = data_in.getStringExtra("appointment_id");
        doctor_name = data_in.getStringExtra("doc_name");

        user_col.document("user_"+patient_email).collection("Consultations").document(appointment_id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();
                    if(document.exists()){

                        consultation_symptoms = document.getString("symptoms_key");
                        consultation_status = document.getString("status_key");
                        consultation_date = document.getString("Appointment_date");
                        slot_details = document.getString("Slot_details_key");

                        text_doc_name.setText(doctor_name);
                        text_date.setText(consultation_date);
                        text_slot.setText(slot_details);
                        text_slot_status.setText(consultation_status);
                        if(consultation_symptoms.equals("")) {
                            text_consultation_symptoms.setText("Symptoms");
                            text_consultation_symptoms.setText(consultation_symptoms);
                        }
                        else{
                            text_consultation_symptoms.setTextColor(Color.parseColor("#FFFFFF"));
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

        split = appointment_id.split("_");
        sessions_date = split[0];
        slot_key = split[1];

        cancel_appointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelAlert();
            }
        });

    }
    private void cancelAlert(){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Confirm");
        builder.setMessage("Are you sure you want to cancel the appointment?");
        builder.setCancelable(false);
        builder.setIcon(R.drawable.ic_baseline_exit_to_app_48);
        builder.setPositiveButton("Cancel consultation", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                user_col.document("user_"+patient_email).collection("Consultations").document(appointment_id).delete();
                user_col.document("user_"+doctor_email).collection("Consultations").document(appointment_id).delete();
                user_col.document("user_"+doctor_email).collection("Sessions").document(sessions_date).update(slot_key,"Free").addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getApplicationContext(),"Consultation cancelled Successfully!",Toast.LENGTH_LONG).show();
                            Intent back_to_home = new Intent( getApplicationContext(),PatientHomePageActivity.class);
                            back_to_home.putExtra("email",patient_email);
                            startActivity(back_to_home);
                            finish();
                        }else{
                            Toast.makeText(getApplicationContext(),"FireBase Error. Unable to Cancel Consultation!",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        builder.setNegativeButton("go back", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alert=builder.create();
        alert.show();
    }

}