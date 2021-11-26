package com.example.medic_app;
import android.app.Activity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
public class PatientDsearchListAdapter extends ArrayAdapter<String> {

    private Activity context;
    private String[] maintitle;
    private String[] subtitle;
    private Integer[] imgid;
    public PatientDsearchListAdapter(Activity context, String[] maintitle, String[] subtitle, Integer[] imgid) {
        super(context, R.layout.patientrecentconsultationslist, maintitle);
        this.context=context;
        this.maintitle=maintitle;
        this.subtitle=subtitle;
        this.imgid=imgid;

    }
    public View getView(int position,View view,ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.patientrecentconsultationslist, null,true);

        TextView titleText = (TextView) rowView.findViewById(R.id.RecentDoctorName);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.DoctorPhoto);
        TextView subtitleText = (TextView) rowView.findViewById(R.id.RecentConsultationTime);

        titleText.setText(maintitle[position]);
        imageView.setImageResource(imgid[position]);
        subtitleText.setText(subtitle[position]);

        return rowView;

    };
}
