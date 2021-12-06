package com.example.medic_app;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DoctorNewSessionActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    Button cancelbtn,bookbtn;
    DoctorNewSessionBookSlotsListViewFragment frag;
    String selected_date,doctor_email="";

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference user_col = db.collection("users");

    DatePickerDialog datePickerDialog ;

    ArrayList<String> existing_session_dates = new ArrayList<String>();
    TextView tvdate;
    int Year, Month, Day;

//    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd");

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_new_session);

        Intent in = getIntent();
        doctor_email = in.getStringExtra("email");

        tvdate = (TextView) findViewById(R.id.doc_session_dates);

        cancelbtn=(Button) findViewById(R.id.nscancelbutton);
        bookbtn=(Button)findViewById(R.id.nsbookbutton);
        ArrayList<Calendar> doc_avail_dates_calendar = new ArrayList<Calendar>();

        cancelbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        frag=new DoctorNewSessionBookSlotsListViewFragment();
        FragmentManager fm=getSupportFragmentManager();
        FragmentTransaction ft=fm.beginTransaction();
        ft.replace(R.id.SessionsSelectFragmentContainer,frag);
        ft.commit();

        tvdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user_col.document("user_"+doctor_email).collection("Sessions").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        doc_avail_dates_calendar.clear();
                        if(task.isSuccessful()){
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                            for(DocumentSnapshot document: task.getResult()){
                                existing_session_dates.add(document.getId());
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

                            datePickerDialog = DatePickerDialog.newInstance(DoctorNewSessionActivity.this, Year, Month, Day);
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
                            datePickerDialog.setTitle("Select New Session Date");
                            Calendar min_date_cal = Calendar.getInstance();
                            Date date_now = new Date();
                            sdf.format(date_now);
                            min_date_cal.setTime(date_now);
                            datePickerDialog.setMinDate(min_date_cal);
                            datePickerDialog.setDisabledDays(doc_avail_dates_cal_array);

                            datePickerDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {

                                @Override
                                public void onCancel(DialogInterface dialogInterface) {

                                    Toast.makeText(getApplicationContext(), "Date Picker Canceled", Toast.LENGTH_SHORT).show();
                                }
                            });
                            datePickerDialog.show(getSupportFragmentManager(), "DatePickerDialog");
                        }else{
                            Toast.makeText(getApplicationContext(), "FireBase Connection Error!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        bookbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> bookedslots=frag.bookedSlots;
                if(selected_date.isEmpty())
                {
                    Toast.makeText(getApplicationContext(), "Select Date First!", Toast.LENGTH_SHORT).show();
                }else if(bookedslots.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Select Slots!",Toast.LENGTH_SHORT).show();
                }else{
                String msg="";
                Map<String,Object> session_slots = new HashMap<>();
                for(int i=0;i<bookedslots.size();i++){
                    session_slots.put(bookedslots.get(i),"Free");
                }
                user_col.document("user_"+doctor_email).collection("Sessions").document(selected_date).set(session_slots).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(getApplicationContext(), "Session Created Successfully", Toast.LENGTH_SHORT).show();
                        Intent doctor_home = new Intent(getApplicationContext(), DoctorHomePageActivity.class);     //Change Doctor Page Here
                        doctor_home.putExtra("email", doctor_email);
                        startActivity(doctor_home);
                    }
                });
                Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_LONG).show();
            }
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onDateSet(DatePickerDialog view, int Year, int Month, int Day) {

        String selected_date_textview = Day+"/"+(Month+1)+"/"+Year;

        LocalDate now = LocalDate.of(Year,Month+1,Day);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd");

        selected_date = dtf.format(now);
        tvdate.setText(selected_date_textview);

        Toast.makeText(getApplicationContext(), "Selected Date:"+selected_date, Toast.LENGTH_SHORT).show();


    }
}