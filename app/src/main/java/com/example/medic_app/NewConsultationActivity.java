package com.example.medic_app;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.model.Document;
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
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class NewConsultationActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    Spinner spinner_doc_list,spinner_slot_list,spinner_doc_dept_list;
    EditText edit_symptoms;
    TextView text_doc_avail_dates;
    ImageView docdp;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference user_col = db.collection("users");

    Button book_consultation,cancel;
    String user_type_key="user_type_key";
    String user_name_key="user_name_key";
    String e_key="email_id_key";
    String patient_email,doc_email,doc_name,doc_department,patient_name,selected_slot_details="";
    String date_today,selected_date,selected_slot,selected_date_textview,symptoms="";

    boolean slot_clash=true;
    int no_of_slots = 16;
    int Year, Month, Day;

    String[] slots = {"Slot1","Slot2","Slot3","Slot4","Slot5","Slot6","Slot7","Slot8",
            "Slot9","Slot10","Slot11","Slot12","Slot13","Slot14","Slot15","Slot16"};
    String[] slot_timings = {"09:00 to 09:30", "09:30 to 10:00","10:00 to 10:30","10:30 to 11:00","11:00 to 11:30",
            "11:30 to 12:00","12:00 to 12:30","12:30 to 13:00","14:00 to 14:30","14:30 to 15:00","15:00 to 15:30",
            "15:30 to 16:00", "16:00 to 16:30","16:30 to 17:00","17:00 to 17:30","17:30 to 18:00"};
    String [] doc_dept={"Orthopaedic","ENT","Cardio","Pediatrician","General Physician"};

    DatePickerDialog datePickerDialog ;

    ArrayList<String> doc_id_list = new ArrayList<String>();
    ArrayList<String> available_dates = new ArrayList<String>();

    ArrayAdapter<String> doc_list_adap,slot_list_adap,doc_dept_list_adap;

    SharedPreferences shp;
    SharedPreferences.Editor ed;
    private static int SPLASH_SCREEN_TIME_OUT=2250;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_consultation);

        //patient_email = getIntent().getStringExtra("email");
        shp = getSharedPreferences("sp", Context.MODE_PRIVATE);
        patient_email = shp.getString("username","");

        if(getIntent().getStringExtra("doc_name")!=null){
            doc_name=getIntent().getStringExtra("doc_name");
        }
        if(getIntent().getStringExtra("doc_department")!=null){
            doc_department=getIntent().getStringExtra("doc_department");
        }
        if(getIntent().getStringExtra("doc_email")!=null){
            doc_email=getIntent().getStringExtra("doc_email");
        }


        spinner_doc_dept_list = (Spinner)findViewById(R.id.new_consul_doc_department_spinner);
        spinner_doc_list = (Spinner)findViewById(R.id.new_consul_doc_spinner);
        spinner_slot_list = (Spinner)findViewById(R.id.time_slot_spinner);
        docdp=(ImageView)findViewById(R.id.PatientProfilePic);
        docdp.setImageResource(R.drawable.doctor1);
        doc_list_adap = new ArrayAdapter(this,R.layout.my_selected_item);
        slot_list_adap = new ArrayAdapter(this, R.layout.my_selected_item);
        doc_dept_list_adap = new ArrayAdapter(this,R.layout.my_selected_item,doc_dept);


        edit_symptoms = (EditText)findViewById(R.id.ncsymptoms);
        text_doc_avail_dates = (TextView)findViewById(R.id.doc_avail_dates);

        book_consultation = (Button)findViewById(R.id.ncbookbutton);
        cancel = (Button)findViewById(R.id.nccancelbutton);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back_to_home = new Intent( getApplicationContext(),PatientHomePageActivity.class);
                back_to_home.putExtra("email",patient_email);
                startActivity(back_to_home);
                finish();
            }
        });


        spinner_doc_dept_list.setAdapter(doc_dept_list_adap);
        if(doc_dept!=null){
            spinner_doc_dept_list.setSelection(doc_dept_list_adap.getPosition(doc_department));
        }
        spinner_doc_dept_list.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                doc_list_adap.clear();
                doc_id_list.clear();
                doc_department = doc_dept[position];
                user_col.whereEqualTo(user_type_key,"Doctor").whereEqualTo("department_key",doc_department).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                doc_name=document.getData().get(user_name_key).toString();
                                String doc_email_temp=document.getData().get(e_key).toString();
                                doc_list_adap.add(doc_name);
                                doc_id_list.add(doc_email_temp);
                            }

                            if(doc_list_adap.isEmpty())
                            {
                                doc_email="";
                                doc_name="";
                                doc_list_adap.clear();
                                doc_list_adap.add("No Doctors Available");
                                doc_id_list.add(null);
                                doc_list_adap.notifyDataSetChanged();
                                Toast.makeText(getApplicationContext(), "No Doctors Exist in selected Department!", Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            doc_list_adap.clear();
                            doc_list_adap.add("No Doctors Available");
                            doc_id_list.add(null);
                            doc_list_adap.notifyDataSetChanged();
                            Toast.makeText(getApplicationContext(), "No Doctors Exist!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner_doc_list.setAdapter(doc_list_adap);

        if(doc_email!=null){
            spinner_doc_list.setSelection(doc_list_adap.getPosition(doc_email));
        }
        spinner_doc_list.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                ArrayList<Calendar> doc_avail_dates_calendar = new ArrayList<Calendar>();

                doc_name = doc_list_adap.getItem(position);
                doc_email = doc_id_list.get(position);
                available_dates.clear();
                slot_list_adap.clear();

                selected_slot = "";
                selected_date = "";

                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd");
                LocalDateTime now = LocalDateTime.now();
                date_today = dtf.format(now);

                //Toast.makeText(getApplicationContext(),doc_name+"\n"+doc_email,Toast.LENGTH_SHORT).show();
                text_doc_avail_dates.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        doc_avail_dates_calendar.clear();
                        //Toast.makeText(getApplicationContext(), doc_name + "\n" + doc_email + "\n" + date_today, Toast.LENGTH_SHORT).show();
                        user_col.document("user_" + doc_email).collection("Sessions").whereGreaterThanOrEqualTo(FieldPath.documentId(), date_today).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                                for (QueryDocumentSnapshot document : task.getResult()) {

                                    Date date = new Date();
                                    try {
                                        date = sdf.parse(document.getId());
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                                    if (date != null) {
                                        Calendar calendar;
                                        calendar = Calendar.getInstance();
                                        calendar.setTime(date);
                                        doc_avail_dates_calendar.add(calendar);
                                    }
                                }


                                if (doc_avail_dates_calendar.isEmpty()) {
                                    slot_list_adap.clear();
                                    slot_list_adap.add("No Slots Available");
                                    slot_list_adap.notifyDataSetChanged();
                                    Toast.makeText(getApplicationContext(), "No Appointments Available", Toast.LENGTH_SHORT).show();
                                    selected_date = "";
                                } else {


                                    datePickerDialog = DatePickerDialog.newInstance(NewConsultationActivity.this, Year, Month, Day);
                                    int nightModeFlag = getApplicationContext().getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
                                    switch (nightModeFlag)
                                    {
                                        case Configuration.UI_MODE_NIGHT_YES:
                                            datePickerDialog.setThemeDark(true);
                                            break;
                                        case Configuration.UI_MODE_NIGHT_NO:
                                            datePickerDialog.setThemeDark(false);
                                            break;
                                        case Configuration.UI_MODE_NIGHT_UNDEFINED:
                                            datePickerDialog.setThemeDark(false);
                                            break;

                                    }
                                    Calendar[] doc_avail_dates_cal_array = new Calendar[doc_avail_dates_calendar.size()];
                                    doc_avail_dates_calendar.toArray(doc_avail_dates_cal_array);
                                    datePickerDialog.setTitle("Select Appointment Date");
                                    datePickerDialog.setSelectableDays(doc_avail_dates_cal_array);

                                    datePickerDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {

                                        @Override
                                        public void onCancel(DialogInterface dialogInterface) {

                                            Toast.makeText(getApplicationContext(), "Date Picker Canceled", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                    datePickerDialog.show(getSupportFragmentManager(), "DatePickerDialog");
                                }
                            }
                        });
                    }
                });
            }
            //nc_doc_spinner_stub
            @Override
            public void onNothingSelected(AdapterView<?> parent){

            }

        });


        spinner_slot_list.setAdapter(slot_list_adap);


            spinner_slot_list.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    selected_slot_details = slot_list_adap.getItem(position);
                    String[] selected_slot_temp = selected_slot_details.split(" ");
                    selected_slot = selected_slot_temp[0] + selected_slot_temp[1];
//                    Toast.makeText(getApplicationContext(), doc_email + "\n" + doc_name + "\n" + patient_email + "\n" + selected_date +
//                            "\n" + selected_slot_details + " selected.", Toast.LENGTH_SHORT).show();

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

        book_consultation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                symptoms = edit_symptoms.getText().toString();
                if(doc_email.isEmpty()||doc_department.isEmpty()||patient_email.isEmpty()||selected_date.isEmpty()||selected_slot.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Select a valid slot before booking consultation!",Toast.LENGTH_SHORT).show();
                }else{
                    user_col.document("user_"+patient_email).collection("Consultations").document(selected_date+"_"+selected_slot)
                            .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                            DocumentSnapshot document = task.getResult();
                            if(document.exists()){
                                slot_clash=true;
                                Toast.makeText(getApplicationContext(),"Slot Clashing with existing booked appointment! Choose another slot!",Toast.LENGTH_LONG).show();
                            }else{
                                slot_clash=false;
                                DocumentReference patient_email_doc = user_col.document("user_"+patient_email);
                                patient_email_doc.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        DocumentSnapshot doc = task.getResult();
                                        patient_name = doc.get("user_name_key").toString();

                                        DocumentReference patient_consultation = user_col.document("user_"+patient_email).collection("Consultations").document(selected_date+"_"+selected_slot);
                                        DocumentReference doctor_consultation = user_col.document("user_"+doc_email).collection("Consultations").document(selected_date+"_"+selected_slot);

                                        Map<String,Object> consultation_data_map = new HashMap<String,Object>();
                                        consultation_data_map.put("doc_email_key",doc_email);
                                        consultation_data_map.put("doc_name_key",doc_name);
                                        consultation_data_map.put("patient_email_key",patient_email);
                                        consultation_data_map.put("patient_name_key",patient_name);
                                        consultation_data_map.put("status_key","Booked");
                                        consultation_data_map.put("Slot_details_key",selected_slot_details);
                                        consultation_data_map.put("Appointment_date",selected_date_textview);
                                        consultation_data_map.put("symptoms_key",symptoms);
                                        consultation_data_map.put("doctor_notes_key","");
                                        consultation_data_map.put("doctor_prescription_key","");

                                        patient_consultation.set(consultation_data_map);
                                        doctor_consultation.set(consultation_data_map);

                                        user_col.document("user_" + doc_email).collection("Sessions")
                                                .document(selected_date).update(selected_slot,"Booked");

                                        LottieAnimationView successview = (LottieAnimationView) findViewById(R.id.suceessco);
                                        successview.playAnimation();
                                        successview.setVisibility(View.VISIBLE);
                                        Toast.makeText(getApplicationContext(),"Consultation Booked Successfully",Toast.LENGTH_LONG).show();
                                        new Handler().postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                Intent back_to_home = new Intent( getApplicationContext(),PatientHomePageActivity.class);
                                                back_to_home.putExtra("email",patient_email);
                                                startActivity(back_to_home);
                                                finish();
                                            }
                                        },SPLASH_SCREEN_TIME_OUT);

                                    }
                                });
                            }
                        }
                    });
                }
            }
        });

    }



    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onDateSet(DatePickerDialog view, int Year, int Month, int Day) {

        selected_date_textview = Day+"/"+(Month+1)+"/"+Year;

        LocalDate now = LocalDate.of(Year,Month+1,Day);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd");

        selected_date = dtf.format(now);
        text_doc_avail_dates.setText(selected_date_textview);

        Toast.makeText(getApplicationContext(), "Selected Date:"+selected_date, Toast.LENGTH_SHORT).show();
        populate_empty_slots(selected_date);


    }

    public void populate_empty_slots(String selected_date){

        user_col.document("user_"+doc_email).collection("Sessions").document(selected_date).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                slot_list_adap.clear();
                if(task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();
                    if(document.exists()){
                        for(int i=0;i<no_of_slots;i++){
                            if(document.getData().get(slots[i])!=null) {
                                String slot_temp = document.getData().get(slots[i]).toString();
                                if (slot_temp.matches("Free")) {
                                    String temp_slot_spinner_value_timings = slot_timings[i];
                                    slot_list_adap.add("Slot " + (i+1) +" - " + temp_slot_spinner_value_timings);

                                }
                            }
                        }
                        if(slot_list_adap.isEmpty()){
                            Toast.makeText(getApplicationContext(),"No Empty Slots Available on Selected Date",Toast.LENGTH_SHORT).show();
                            slot_list_adap.clear();
                            slot_list_adap.add("No Slots Available");
                            slot_list_adap.notifyDataSetChanged();
                        }else {

                            slot_list_adap.notifyDataSetChanged();
                        }
                    }else{
                        Toast.makeText(getApplicationContext(),"No Empty Slots Available for Selected Date",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(), task.getException().toString()+" FireBase Connection ERROR!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

}

