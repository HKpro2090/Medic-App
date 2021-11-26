package com.example.medic_app;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.type.DateTime;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class NewConsultationActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    Spinner spinner_doc_list,spinner_slot_list;
    EditText edit_doc_name;
    TextView text_doc_avail_dates;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference user_col = db.collection("users");
    String user_type_key="user_type_key";
    String user_name_key="user_name_key";
    String e_key="email_id_key";
    String doc_email,doc_name="";
    String date_today,selected_date,slot="";

    DatePickerDialog datePickerDialog ;
    int Year, Month, Day, Hour, Minute;
    Calendar calendar ;


    ArrayList<String> doc_id_list = new ArrayList<String>();
    ArrayList<String> available_dates = new ArrayList<String>();
    ArrayList<Date> doc_avail_dates = new ArrayList<Date>();
    ArrayList<Calendar> doc_avail_dates_calendar = new ArrayList<Calendar>();
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
        text_doc_avail_dates = (TextView)findViewById(R.id.doc_avail_dates);

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
                        calendar = Calendar.getInstance();
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                        for (QueryDocumentSnapshot document : task.getResult()) {

                            Date date = new Date();
                            try {
                                date=sdf.parse(document.getId());
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            if(date != null){
                            calendar.setTime(date);
                            }
                            doc_avail_dates_calendar.add(calendar);
                            doc_avail_dates.add(date);
                            slot_list_adap.add(document.getId().toString());
                        }
                    spinner_slot_list.setAdapter(slot_list_adap);
                    }
                });
            }

            //nc_doc_spinner_stub

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });

        text_doc_avail_dates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                datePickerDialog = DatePickerDialog.newInstance(NewConsultationActivity.this, Year, Month, Day);
                datePickerDialog.setTitle("Select Appointment Date");
                Calendar min_date_c = Calendar.getInstance();
                datePickerDialog.setMinDate(min_date_c);
                Calendar[] doc_avail_dates_cal_array = new Calendar[doc_avail_dates_calendar.size()];
                doc_avail_dates_calendar.toArray(doc_avail_dates_cal_array);
                datePickerDialog.setSelectableDays(doc_avail_dates_cal_array);

                datePickerDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {

                    @Override
                    public void onCancel(DialogInterface dialogInterface) {

                        Toast.makeText(getApplicationContext(), "Date picker Canceled", Toast.LENGTH_SHORT).show();
                    }
                });

                datePickerDialog.show(getSupportFragmentManager(), "DatePickerDialog");

            }
        });

    }

    @Override
    public void onDateSet(DatePickerDialog view, int Year, int Month, int Day) {

        String sel_date = "Date: "+Day+"/"+(Month+1)+"/"+Year;
        selected_date = "" + Year + (Month+1) +Day;
        text_doc_avail_dates.setText(sel_date);
        Toast.makeText(getApplicationContext(), "Selected Date:"+selected_date, Toast.LENGTH_SHORT).show();

    }
}

