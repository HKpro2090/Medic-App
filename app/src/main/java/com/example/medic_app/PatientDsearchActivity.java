package com.example.medic_app;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class PatientDsearchActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{
    private SearchView search;
    private ListView lv;
    private ArrayList<Doctor> doctorArrayList;
    private PatientDsearchListAdapter adp;
    String[] ids={"Doctor ID:11525","Doctor ID:21222","Doctor ID:18626"};
    String[] doctors={"Dr.abc","Dr.pqr","Dr.xyz"};
    Integer[] imgid={R.drawable.patient1,R.drawable.patient2,R.drawable.patient1};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_dsearch);
        search=(SearchView)findViewById(R.id.doctorSearchView);
        lv=(ListView)findViewById(R.id.doclistview);
        doctorArrayList=new ArrayList<Doctor>();
        for(int i=0;i<3;i++) {
            doctorArrayList.add(new Doctor(doctors[i], ids[i], imgid[i]));
        }
        adp=new PatientDsearchListAdapter(this,doctorArrayList);
        lv.setAdapter(adp);
        lv.set
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if(Arrays.asList(doctors).contains(query)){
                    adp.getFilter().filter(query);
                }else{
                    Toast.makeText(PatientDsearchActivity.this, "No Match found",Toast.LENGTH_LONG).show();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    @Override
    public boolean onQueryTextChange(String newText)
    {

        if (TextUtils.isEmpty(newText)) {
            mListView.clearTextFilter();
        } else {
            mListView.setFilterText(newText);
        }
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query)
    {
        return false;
    }
}