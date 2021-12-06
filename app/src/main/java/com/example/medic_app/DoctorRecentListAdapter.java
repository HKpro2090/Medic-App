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

import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class DoctorRecentListAdapter extends ArrayAdapter<String> {

    public Activity context;
    public ArrayList<String> maintitle;
    public ArrayList<String> subtitle;
    public ArrayList<String> doc_email;
    public ArrayList<Integer> imgid;

    public String patient_email;


    public DoctorRecentListAdapter(Fragment context, ArrayList maintitle, ArrayList subtitle, ArrayList imgid, String patient_email, ArrayList doc_email) {
        super(context.getActivity(), R.layout.doctorrecentconsultationlist, maintitle);
        this.context=context.getActivity();
        this.maintitle=maintitle;
        this.subtitle=subtitle;
        this.imgid=imgid;
        this.patient_email=patient_email;
        this.doc_email=doc_email;
    }

    public View getView(int position,View view,ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.doctorrecentconsultationlist, null,false);
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
//                in.putExtra("email",patient_email);
//                in.putExtra("doc_name",maintitle.get(position));
//                in.putExtra("doc_email",doc_email.get(position));
//                context.startActivity(in);
//            }
//        });
        return rowView;

    };
}
