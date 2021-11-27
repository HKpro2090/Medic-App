package com.example.medic_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
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
import java.util.Arrays;

public class PatientDsearchActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{
    private SearchView search;
    private ListView lv;
    private ArrayList<Doctor> doctorArrayList;
    private PatientDsearchListAdapter adp;

    String user_type_key="user_type_key";
    String doc_name,doc_email,doc_department="";
    String patient_email="";


//    String[] ids={"Doctor ID:11525","Doctor ID:21222","Doctor ID:18626"};
//    String[] doctors={"Dr.abc","Dr.pqr","Dr.xyz"};
    Integer[] imgid={R.drawable.patient1,R.drawable.patient2,R.drawable.patient1};

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference user_col = db.collection("users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_dsearch);
        search=(SearchView)findViewById(R.id.doctorSearchView);
        lv=(ListView)findViewById(R.id.doclistview);
        doctorArrayList=new ArrayList<Doctor>();

        patient_email = getIntent().getStringExtra("email");

        user_col.whereEqualTo(user_type_key,"Doctor").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        doc_name=document.getData().get("user_name_key").toString();
                        doc_email=document.getData().get("email_id_key").toString();
                        doc_department = document.getData().get("department_key").toString();
                        doctorArrayList.add(new Doctor(doc_name,doc_email,doc_department, imgid[0]));
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "No Doctors Exist!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        adp=new PatientDsearchListAdapter(this,doctorArrayList,patient_email);
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
}