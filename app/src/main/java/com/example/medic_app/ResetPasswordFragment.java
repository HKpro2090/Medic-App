package com.example.medic_app;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class ResetPasswordFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_reset_password, container, false);

        Button fpassbtn=(Button)view.findViewById(R.id.resetPassEmailButton);
        fpassbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).imageresize(0.32f);
                ((MainActivity)getActivity()).reloadimg(R.drawable.forgot_password);
                ((MainActivity)getActivity()).makefragmentbig(0.77f);
                FragmentManager m=getFragmentManager();
                FragmentTransaction ft=m.beginTransaction();
                ft.replace(R.id.RegistrationFrame,new ChangePasswordFragment());
                ft.commit();
            }
        });

        return view;
    }
}