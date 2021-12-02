package com.example.medic_app;
import android.app.Activity;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class DoctorPsearchListAdapter extends BaseAdapter implements Filterable {

    private Context context;
    public ArrayList<Doctor> patientArrayList;
    public ArrayList<Doctor> orig;
    public String doctor_email;
    public DoctorPsearchListAdapter(Activity context, ArrayList<Doctor> patientArrayList, String doctor_email) {
        super();
        this.context=context;
        this.patientArrayList =patientArrayList;
        this.doctor_email =doctor_email;
    }
    public class DoctorHolder
    {
        TextView name;
        TextView dept;
        ImageView dp;
    }

    public Filter getFilter() {
        return new Filter() {

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                final FilterResults oReturn = new FilterResults();
                final ArrayList<Doctor> results = new ArrayList<Doctor>();
                if (orig == null)
                    orig = patientArrayList;
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
                patientArrayList = (ArrayList<Doctor>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        return patientArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return patientArrayList.get(position);
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
            holder.dept =(TextView) convertView.findViewById(R.id.RecentConsultationTime);
            holder.dp=(ImageView)convertView.findViewById(R.id.DoctorPhoto);
            convertView.setTag(holder);
        }
        else
        {
            holder=(DoctorHolder) convertView.getTag();
        }

        holder.name.setText(patientArrayList.get(position).getName());
        holder.dept.setText(patientArrayList.get(position).getDepartment());
        holder.dp.setImageResource(patientArrayList.get(position).getDp());
        holder.dp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Doctor temp_doc = patientArrayList.get(position);
                String doc_email = temp_doc.getId();
                String doc_name = temp_doc.getName();
                String doc_department = temp_doc.getDepartment();

                Intent in=new Intent(context.getApplicationContext(),Doctor_PatientInfoPageActivity.class);

                in.putExtra("doc_email",doc_email);
                in.putExtra("doc_department",doc_department);
                in.putExtra("doc_name",doc_name);
                in.putExtra("email", doctor_email);

                context.startActivity(in);
                //Toast.makeText(context.getApplicationContext(),doctorArrayList.get(position).getName(),Toast.LENGTH_LONG).show();
            }
        });

        holder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Doctor temp_doc = patientArrayList.get(position);
                String doc_email = temp_doc.getId();
                String doc_name = temp_doc.getName();
                String doc_department = temp_doc.getDepartment();

                Intent in=new Intent(context.getApplicationContext(),Doctor_PatientInfoPageActivity.class);

                in.putExtra("doc_email",doc_email);
                in.putExtra("doc_department",doc_department);
                in.putExtra("doc_name",doc_name);
                in.putExtra("email", doctor_email);

                //Toast.makeText(context.getApplicationContext(),doc_email,Toast.LENGTH_LONG).show();
                context.startActivity(in);
                //Toast.makeText(context.getApplicationContext(),doctorArrayList.get(position).getName(),Toast.LENGTH_LONG).show();
            }
        });
        return convertView;

    }

}
