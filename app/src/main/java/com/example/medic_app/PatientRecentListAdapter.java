package com.example.medic_app;
import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class PatientRecentListAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] maintitle;
    private final String[] subtitle;
    private final Integer[] imgid;
    public PatientRecentListAdapter(PatientRecentConsultationsFragment context, String[] maintitle, String[] subtitle, Integer[] imgid) {
        super(context.getActivity(), R.layout.patientupcomingconsultationslist, maintitle);
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
        titleText.setText(maintitle[position]);
        subtitleText.setText(subtitle[position]);
        imageView.setImageResource(imgid[position]);
        return rowView;

    };
}
