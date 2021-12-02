package com.example.medic_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ListView;
import android.widget.SearchView;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class DoctorSearchPatientActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {
    private SearchView search;
    private ListView lv;
    private ArrayList<Doctor> doctorArrayList;
    private DoctorPsearchListAdapter adp;

    String user_type_key="user_type_key";
    String doc_name,doc_email,doc_department="";
    String patient_email="";
    Integer[] imgid={R.drawable.patient1,R.drawable.patient2,R.drawable.patient1};
//    FirebaseFirestore db = FirebaseFirestore.getInstance();
//    CollectionReference user_col = db.collection("users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_search_patient);
        search=(SearchView)findViewById(R.id.patientSearchView);
        lv=(ListView)findViewById(R.id.patientlistview);
        doctorArrayList=new ArrayList<Doctor>();
        try{
            patient_email = getIntent().getStringExtra("email");
        }catch (Exception e){patient_email="";}
        doctorArrayList.add(new Doctor("patient 1","p1@123.com","", imgid[0]));
        doctorArrayList.add(new Doctor("patient 2","p2@1765.com","", imgid[1]));
        doctorArrayList.add(new Doctor("patient 3","p0@314.com","", imgid[2]));

        adp=new DoctorPsearchListAdapter(this,doctorArrayList,patient_email);
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