package com.example.medic_app;
import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class PatientRecentListAdapter extends ArrayAdapter<String> {

    public Activity context;
    public ArrayList<String> maintitle;
    public ArrayList<String> subtitle;
    public ArrayList<Integer> imgid;

    public PatientRecentListAdapter(PatientRecentConsultationsFragment context, ArrayList maintitle, ArrayList subtitle, ArrayList imgid) {
        super(context.getActivity(), R.layout.patientrecentconsultationslist, maintitle);
        this.context=context.getActivity();
        this.maintitle=maintitle;
        this.subtitle=subtitle;
        this.imgid=imgid;

    }

    public View getView(int position,View view,ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.patientrecentconsultationslist, null,true);
        TextView titleText = (TextView) rowView.findViewById(R.id.RecentDoctorName);
        TextView subtitleText = (TextView) rowView.findViewById(R.id.RecentConsultationTime);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.DoctorPhoto);
        titleText.setText(maintitle.get(position));
        subtitleText.setText(subtitle.get(position));
        imageView.setImageResource(imgid.get(position));
//        imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent in=new Intent(getContext(),PatientDinfoActivity.class);
//                in.putExtra("name",maintitle[position]);
//                in.putExtra("dp",imgid[position]);
//                context.startActivity(in);
//            }
//        });
        return rowView;

    };
}
