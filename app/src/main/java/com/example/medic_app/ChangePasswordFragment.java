package com.example.medic_app;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class ChangePasswordFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.change_password_fragment_layout, container, false);
        Button cancel=(Button)view.findViewById(R.id.CancelChangePasswordButton);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).imageresize(0.32f);
                ((MainActivity)getActivity()).reloadimg();
                ((MainActivity)getActivity()).makefragmentbig(0.54f);

                FragmentManager m=getFragmentManager();
                FragmentTransaction ft=m.beginTransaction();
                ft.replace(R.id.RegistrationFrame,new LoginFragment());
                ft.commit();
            }
        });

        Button change =(Button)view.findViewById(R.id.ChangePasswordButton);
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).imageresize(0.32f);
                ((MainActivity)getActivity()).reloadimg();
                ((MainActivity)getActivity()).makefragmentbig(0.54f);
                FragmentManager m=getFragmentManager();
                FragmentTransaction ft=m.beginTransaction();
                ft.replace(R.id.RegistrationFrame,new LoginFragment());
                ft.commit();
            }
        });

        return view;
    }
}