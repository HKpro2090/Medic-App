package com.example.medic_app;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;


public class DoctorNewSessionBookSlotsListViewFragment extends Fragment {
    String[] slots = {"Slot1","Slot2","Slot3","Slot4","Slot5","Slot6","Slot7","Slot8",
            "Slot9","Slot10","Slot11","Slot12","Slot13","Slot14","Slot15","Slot16"};
    String[] slot_timings = {"09:00 to 09:30", "09:30 to 10:00","10:00 to 10:30","10:30 to 11:00","11:00 to 11:30",
            "11:30 to 12:00","12:00 to 12:30","12:30 to 13:00","14:00 to 14:30","14:30 to 15:00","15:00 to 15:30",
            "15:30 to 16:00", "16:00 to 16:30","16:30 to 17:00","17:00 to 17:30","17:30 to 18:00"};
    ArrayList<String> slotsAL = new ArrayList<>();
    ArrayList<String> slot_timingsAL = new ArrayList<>();
    ArrayList<String> bookedSlots=new ArrayList<>();
//    ArrayList<String> blockedSlots=new ArrayList<>();
    PatientUpcomingListAdapter lv_adapter;
    ListView lv;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_doctor_new_session_book_slots_list_view, container, false);
        lv=(ListView) view.findViewById(R.id.nsbookslotslv);
        for(int i=0;i<16;i++){
            slotsAL.add(slots[i]);
            slot_timingsAL.add(slot_timings[i]);
        }
        lv_adapter = new PatientUpcomingListAdapter(DoctorNewSessionBookSlotsListViewFragment.this, slot_timingsAL, slotsAL);
        lv_adapter.notifyDataSetChanged();
        lv.setAdapter(lv_adapter);
//        blockedSlots.add(slots[1]+"-"+slot_timings[1]);
//        blockedSlots.add(slots[4]+"-"+slot_timings[4]);
//        int index1=bookedSlots.indexOf(blockedSlots.get(0));
//        int index2=bookedSlots.indexOf(blockedSlots.get(1));
//        View v = (View)lv.getItemAtPosition(index1);
//        View v2 = (View)lv.getItemAtPosition(index2);
//        v.setBackgroundColor(getResources().getColor(R.color.teal_200));
//        v2.setBackgroundColor(getResources().getColor(R.color.teal_200));
//        lv.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                bookedSlots.add(slots[position]);
//                view.setBackgroundColor(getResources().getColor(R.color.teal_200));
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//                bookedSlots.clear();
//            }
//
//        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(!(bookedSlots.contains(slots[position]))) {
                    bookedSlots.add(slots[position]);
                    view.setBackgroundColor(getResources().getColor(R.color.teal_200));
                }
                else{
                    bookedSlots.remove(slots[position]);
                    view.setBackground(getResources().getDrawable(R.drawable.textview_border));
//                    int nightModeFlags =
//                            getContext().getResources().getConfiguration().uiMode &
//                                    Configuration.UI_MODE_NIGHT_MASK;
//                    switch (nightModeFlags) {
//                        case Configuration.UI_MODE_NIGHT_YES:
//                            view.setBackground(getResources().getDrawable(R.drawable.textview_border));
//                            break;
//
//                        case Configuration.UI_MODE_NIGHT_NO:
//                            view.setBackgroundColor(Color.parseColor("#FFFFFF"));
//                            break;
//
//                        case Configuration.UI_MODE_NIGHT_UNDEFINED:
//                            view.setBackgroundColor(Color.parseColor("#FFFFFF"));
//                            break;
//                    }

                }
            }
        });



        return view;
    }
}