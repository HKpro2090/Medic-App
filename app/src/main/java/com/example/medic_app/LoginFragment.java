package com.example.medic_app;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

public class LoginFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.login_fragment_layout, container, false);
        // Inflate the layout for this fragment
        Button sgnupbtn=(Button)view.findViewById(R.id.SignUpButton);
        sgnupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager m=getFragmentManager();
                FragmentTransaction ft=m.beginTransaction();
                ft.replace(R.id.RegistrationFrame,new SignupFragment());
                ft.commit();
            }
        });
        return view;
    }
}