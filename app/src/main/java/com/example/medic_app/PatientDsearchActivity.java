package com.example.medic_app;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.Arrays;

public class PatientDsearchActivity extends AppCompatActivity {
    String[] ids={"Doctor ID:11525","Doctor ID:21222","Doctor ID:18626"};
    String[] doctors={"Dr.abc","Dr.pqr","Dr.xyz"};
    Integer[] imgid={R.drawable.patient1,R.drawable.patient2,R.drawable.patient1};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_dsearch);
        SearchView search=(SearchView)findViewById(R.id.doctorSearchView);
        ListView lv=(ListView)findViewById(R.id.doclistview);
        PatientDsearchListAdapter adapter=new PatientDsearchListAdapter(this,doctors,ids,imgid);
        lv.setAdapter(adapter);

        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if(Arrays.asList(doctors).contains(query)){
                    adapter.getFilter().filter(query);
                }else{
                    Toast.makeText(PatientDsearchActivity.this, "No Match found",Toast.LENGTH_LONG).show();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(Arrays.asList(doctors).contains(newText)){
                    adapter.getFilter().filter(newText);
                }else{
                    Toast.makeText(PatientDsearchActivity.this, "No Match found",Toast.LENGTH_LONG).show();
                }
                return false;
            }
        });
    }
}