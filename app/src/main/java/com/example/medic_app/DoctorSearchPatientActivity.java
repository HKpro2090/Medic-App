package com.example.medic_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class DoctorSearchPatientActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {
    private SearchView search;
    private ListView lv;
    private ArrayList<Doctor> doctorArrayList;
    private DoctorPsearchListAdapter adp;

    String user_type_key="user_type_key";
    String patient_name,doc_email,doc_department="";
    String patient_email="";
    Integer[] imgid={R.drawable.patient1,R.drawable.patient2,R.drawable.patient1};
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference user_col = db.collection("users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_search_patient);

        Intent in = getIntent();
        doc_email = in.getStringExtra("email");

        search=(SearchView)findViewById(R.id.patientSearchView);
        lv=(ListView)findViewById(R.id.patientlistview);
        doctorArrayList=new ArrayList<Doctor>();
        user_col.whereEqualTo(user_type_key,"Patient").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                doctorArrayList.clear();
                if(task.isSuccessful()){
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        patient_name=document.getData().get("user_name_key").toString();
                        patient_email=document.getData().get("email_id_key").toString();
                        doc_department = "";
                        doctorArrayList.add(new Doctor(patient_name,patient_email,doc_department, imgid[0]));
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "No Doctors Exist!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        adp=new DoctorPsearchListAdapter(this,doctorArrayList,patient_email);
        lv.setEmptyView(findViewById(R.id.emptysearch2));
        lv.setAdapter(adp);
        lv.setTextFilterEnabled(true);
        setupSearchView();

    }

    private void setupSearchView()
    {
        search.setIconifiedByDefault(false);
        search.setOnQueryTextListener(this);
        search.setSubmitButtonEnabled(true);
        search.setQueryHint("Search Here");
    }

    @Override
    public boolean onQueryTextChange(String newText)
    {

        if (TextUtils.isEmpty(newText)) {
            lv.clearTextFilter();
        } else {
            lv.setFilterText(newText);
        }
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query)
    {
        return false;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}