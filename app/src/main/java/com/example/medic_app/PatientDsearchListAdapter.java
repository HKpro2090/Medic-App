package com.example.medic_app;
import android.app.Activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class PatientDsearchListAdapter extends BaseAdapter implements Filterable {

    private Context context;
    public ArrayList<Doctor> doctorArrayList;
    public ArrayList<Doctor> orig;
    public PatientDsearchListAdapter(Activity context, ArrayList<Doctor> doctorArrayList) {
        super();
        this.context=context;
        this.doctorArrayList=doctorArrayList;

    }
    public class DoctorHolder
    {
        TextView name;
        TextView id;
        ImageView dp;
    }

    public Filter getFilter() {
        return new Filter() {

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                final FilterResults oReturn = new FilterResults();
                final ArrayList<Doctor> results = new ArrayList<Doctor>();
                if (orig == null)
                    orig = doctorArrayList;
                if (constraint != null) {
                    if (orig != null && orig.size() > 0) {
                        for (final Doctor g : orig) {
                            if (g.getName().toLowerCase().contains(constraint.toString()))
                                results.add(g);
                        }
                    }
                    oReturn.values = results;
                }
                return oReturn;
            }

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint,
                                          FilterResults results) {
                doctorArrayList = (ArrayList<Doctor>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        return doctorArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return doctorArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }




    public View getView(int position, View convertView, ViewGroup parent) {
        DoctorHolder holder;
        if(convertView==null)
        {
            convertView=LayoutInflater.from(context).inflate(R.layout.patientrecentconsultationslist, parent, false);
            holder=new DoctorHolder();
            holder.name=(TextView) convertView.findViewById(R.id.RecentDoctorName);
            holder.id=(TextView) convertView.findViewById(R.id.RecentConsultationTime);
            holder.dp=(ImageView)convertView.findViewById(R.id.DoctorPhoto);
            convertView.setTag(holder);
        }
        else
        {
            holder=(DoctorHolder) convertView.getTag();
        }

        holder.name.setText(doctorArrayList.get(position).getName());
        holder.id.setText(String.valueOf(doctorArrayList.get(position).getId()));
        holder.dp.setImageResource(doctorArrayList.get(position).getDp());

        return convertView;

    }

}
