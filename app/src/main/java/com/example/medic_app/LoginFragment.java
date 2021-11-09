package com.example.medic_app;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

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
                ((MainActivity)getActivity()).imageresize(0.1f);
                ((MainActivity)getActivity()).makeimgempty();
                ((MainActivity)getActivity()).makefragmentbig(0.77f);
                FragmentManager m=getFragmentManager();
                FragmentTransaction ft=m.beginTransaction();
                ft.replace(R.id.RegistrationFrame,new SignupFragment());
                ft.commit();

            }
        });
        Button fpassbtn=(Button)view.findViewById(R.id.ForgotPasswordButton);
        fpassbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).imageresize(0.1f);
                ((MainActivity)getActivity()).makeimgempty();
                ((MainActivity)getActivity()).makefragmentbig(0.77f);
                FragmentManager m=getFragmentManager();
                FragmentTransaction ft=m.beginTransaction();
                ft.replace(R.id.RegistrationFrame,new ChangePassword());
                ft.commit();
            }
        });
        Button sib=(Button)view.findViewById(R.id.SigninPasswordButton);
        sib.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   Intent in=new Intent(getActivity(), PatientHomePageActivity.class);
                   startActivity(in);
               }
            }
        );
        return view;
    }
}