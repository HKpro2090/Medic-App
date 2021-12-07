package com.example.medic_app;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
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
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class DoctorSessionsFragment extends Fragment  implements DatePickerDialog.OnDateSetListener{

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference user_col = db.collection("users");
    String doctor_email,selected_date="";
    ArrayList<String> existing_session_dates = new ArrayList<String>();
    int Year, Month, Day;
    DatePickerDialog datePickerDialog;
    ImageView drdp;
    TextView text_date_picker;
    public DoctorHomePageActivity dha;
    public String e_key;
    public String email;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_doctor_sessions, container, false);
        dha=(DoctorHomePageActivity) getActivity();
        e_key= dha.e_key;
        email=dha.email;
        text_date_picker = (TextView) view.findViewById(R.id.datePickerView);
        doctor_email = getArguments().getString("email");
        ArrayList<Calendar> doc_avail_dates_calendar = new ArrayList<Calendar>();
        drdp=(ImageView)view.findViewById(R.id.DoctorSProfilePic);
        drdp.setImageResource(R.drawable.doctor1);
        drdp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent doctor_settings_page = new Intent(getActivity(), SettingsPageActivity.class);
                doctor_settings_page.putExtra(e_key, email);
                startActivity(doctor_settings_page);
            }
        });
        Date date_today = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        selected_date = sdf.format(date_today);
        SimpleDateFormat tv_date = new SimpleDateFormat("dd/MM/yyyy");
        String selected_date_textview = tv_date.format(date_today);
        text_date_picker.setText(selected_date_textview);

        FragmentManager fm=getChildFragmentManager();
        FragmentTransaction ft= fm.beginTransaction();
        Bundle data = new Bundle();
        data.putString("doc_email",doctor_email);
        data.putString("session_date",selected_date);
        Fragment Sessions_List = new DoctorDailyAppointmentsListFragment();
        Sessions_List.setArguments(data);
        ft.replace(R.id.SessionsPreviewFragmentContainer,Sessions_List);
        ft.commit();

        text_date_picker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user_col.document("user_"+doctor_email).collection("Sessions").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        doc_avail_dates_calendar.clear();
                        if (task.isSuccessful()) {
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                            for (DocumentSnapshot document : task.getResult()) {
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

                            datePickerDialog = DatePickerDialog.newInstance(DoctorSessionsFragment.this, Year, Month, Day);
                            int nightModeFlag = getContext().getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
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
                            datePickerDialog.setTitle("Select Session Date");
                            datePickerDialog.setSelectableDays(doc_avail_dates_cal_array);
                            datePickerDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {

                                @Override
                                public void onCancel(DialogInterface dialogInterface) {
                                    Toast.makeText(getContext(), "Date Picker Canceled", Toast.LENGTH_SHORT).show();
                                }
                            });
                            datePickerDialog.show(getChildFragmentManager(), "DatePickerDialog");
                        }
                    }
                });
            }
        });

        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onDateSet(DatePickerDialog view, int Year, int Month, int Day) {

        String selected_date_textview = Day+"/"+(Month+1)+"/"+Year;

        LocalDate now = LocalDate.of(Year,Month+1,Day);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd");

        selected_date = dtf.format(now);
        text_date_picker.setText(selected_date_textview);

        Toast.makeText(getContext(), "Selected Date:"+selected_date, Toast.LENGTH_SHORT).show();

        FragmentManager fm=getChildFragmentManager();
        FragmentTransaction ft= fm.beginTransaction();
        Bundle data = new Bundle();
        data.putString("doc_email",doctor_email);
        data.putString("session_date",selected_date);
        Fragment Sessions_List = new DoctorDailyAppointmentsListFragment();
        Sessions_List.setArguments(data);
        ft.replace(R.id.SessionsPreviewFragmentContainer,Sessions_List);
        ft.commit();

    }
}