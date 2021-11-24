package com.example.medic_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;

public class NewConsultationActivity extends AppCompatActivity {

    Spinner spinner_doc_list;
    EditText edit_doc_name;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference user_col = db.collection("users");
    String user_type_key="user_type_key";
    String user_name_key="user_name_key";
    String e_key="email_id_key";
    String doc_email,doc_name="";

    ArrayList<String> doc_id_list = new ArrayList<String>();
    ArrayAdapter<String> doc_list_adap;
    String[] doc_list = {""};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_consultation);
        spinner_doc_list = (Spinner)findViewById(R.id.new_consul_doc_spinner);
        doc_list_adap = new ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item);

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

    }
}