package com.example.medic_app;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.medic_app.R;

public class PatientUpcomingListAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] maintitle;
    private final String[] subtitle;

    public PatientUpcomingListAdapter(PatientUpcomingConsultationsFragment context, String[] maintitle, String[] subtitle) {
        super(context.getActivity(), R.layout.patientupcominglist, maintitle);
        this.context=context.getActivity();
        this.maintitle=maintitle;
        this.subtitle=subtitle;


    }

    public View getView(int position,View view,ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.patientupcominglist, null,true);
        TextView titleText = (TextView) rowView.findViewById(R.id.titlepul);
        TextView subtitleText = (TextView) rowView.findViewById(R.id.doctorpul);
        titleText.setText(maintitle[position]);
        subtitleText.setText(subtitle[position]);
        return rowView;

    };
}
