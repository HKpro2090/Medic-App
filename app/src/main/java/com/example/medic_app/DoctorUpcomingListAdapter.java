package com.example.medic_app;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class DoctorUpcomingListAdapter extends ArrayAdapter<String> {

    public Activity context;
    public ArrayList<String> maintitle;
    public ArrayList<String> subtitle;


    public DoctorUpcomingListAdapter(Fragment context, ArrayList maintitle, ArrayList subtitle) {
        super(context.getActivity(), R.layout.doctorupcomingconsultationlist,maintitle);
        this.context = context.getActivity();
        this.maintitle=maintitle;
        this.subtitle=subtitle;


    }

    public View getView(int position,View view,ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.doctorupcomingconsultationlist, null,false);
        TextView titleText = (TextView) rowView.findViewById(R.id.UpcomingTime);
        TextView subtitleText = (TextView) rowView.findViewById(R.id.UpcomingDoctorName);
        titleText.setText(maintitle.get(position));
        subtitleText.setText(subtitle.get(position));
        return rowView;

    };
}
