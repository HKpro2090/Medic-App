package com.example.medic_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

public class PatientDinfoActivity extends AppCompatActivity {

    String patient_email,doc_email,doc_name,doc_department,doc_qualification,doc_about="";
    TextView text_doc_name,text_doc_qualification,text_doc_department,text_doc_about;
    ImageView doc_image;
    Button book_appointment;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference user_col = db.collection("users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_dinfo);

        Intent i=getIntent();
        patient_email=i.getStringExtra("email");
        doc_email=i.getStringExtra("doc_email");
        doc_department=i.getStringExtra("doc_department");
        doc_name = i.getStringExtra("doc_name");

//        int image=i.getIntExtra("dp",R.drawable.patient2);
//        doc_image=(ImageView)findViewById(R.id.doctorinfoimge);
//        doc_image.setImageResource(image);

        text_doc_name=(TextView)findViewById(R.id.dinfonametv);
        text_doc_qualification=(TextView)findViewById(R.id.dinfoqualtv);
        text_doc_department=(TextView)findViewById(R.id.dinfodeptv);
        text_doc_about =(TextView)findViewById(R.id.dinfoabouttv);

        book_appointment = (Button)findViewById(R.id.bookAppointmentButton);

        user_col.document("user_"+doc_email).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if(task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();
                    if(document.exists()){
                        doc_qualification=document.getString("Qualification_key");
                        doc_about=document.getString("about_key");
                        text_doc_name.setText(doc_name);
                        text_doc_about.setText(doc_about);
                        text_doc_department.setText(doc_department);
                        text_doc_qualification.setText(doc_qualification);
                    }else{
                        Toast.makeText(getApplicationContext(),"Doctor Not Found!",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(),"FireBase Connection Error!",Toast.LENGTH_SHORT).show();
                }
            }
        });



        book_appointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent next = new Intent(getApplicationContext(),NewConsultationActivity.class);
                next.putExtra("doc_email",doc_email);
                next.putExtra("doc_department",doc_department);
                next.putExtra("doc_name",doc_name);
                next.putExtra("email",patient_email);
                startActivity(next);
            }
        });
    }
}