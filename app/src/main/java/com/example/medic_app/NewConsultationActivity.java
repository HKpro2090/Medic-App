package com.example.medic_app;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.type.DateTime;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;

public class NewConsultationActivity extends AppCompatActivity {

    Spinner spinner_doc_list,spinner_slot_list;
    EditText edit_doc_name;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference user_col = db.collection("users");
    String user_type_key="user_type_key";
    String user_name_key="user_name_key";
    String e_key="email_id_key";
    String doc_email,doc_name="";
    String date_today,slot="";


    ArrayList<String> doc_id_list = new ArrayList<String>();
    ArrayList<String> available_dates = new ArrayList<String>();
    ArrayAdapter<String> doc_list_adap,slot_list_adap;
    String[] doc_list = {""};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_consultation);

        spinner_doc_list = (Spinner)findViewById(R.id.new_consul_doc_spinner);
        spinner_slot_list = (Spinner)findViewById(R.id.time_slot_spinner);

        doc_list_adap = new ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item);
        slot_list_adap = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item);

        edit_doc_name = (EditText)findViewById(R.id.ncdoctorname);

        user_col.whereEqualTo(user_type_key,"Doctor").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                         doc_name=document.getData().get(user_name_key).toString();
                         doc_email=document.getData().get(e_key).toString();
                         doc_list_adap.add(doc_name);
                         doc_id_list.add(doc_email);
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "User Doesn't Exits!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        spinner_doc_list.setAdapter(doc_list_adap);

        spinner_doc_list.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                doc_name = doc_list_adap.getItem(position);
                doc_email = doc_id_list.get(position);
                edit_doc_name.setText(doc_name);
                available_dates.clear();
                slot_list_adap.clear();

                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd");
                LocalDateTime now = LocalDateTime.now();
                date_today = dtf.format(now);

                user_col.document("user_"+doc_email).collection("Sessions").whereGreaterThanOrEqualTo(FieldPath.documentId(),date_today).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            slot_list_adap.add(document.getId().toString());
                        }
                    spinner_slot_list.setAdapter(slot_list_adap);
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
}