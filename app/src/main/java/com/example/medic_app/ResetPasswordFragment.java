package com.example.medic_app;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ResetPasswordFragment extends Fragment {

    String email,new_password="";
    String e_key="email";
    EditText edit_reset_pass_email;
    boolean form_validated = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_reset_password, container, false);
        Button fpassbtn=(Button)view.findViewById(R.id.resetPassEmailButton);
        edit_reset_pass_email = (EditText) view.findViewById(R.id.resetPassEmailField);
        Bundle b = getArguments();
        email = b.getString(e_key);
        edit_reset_pass_email.setText(email);

        fpassbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                email= edit_reset_pass_email.getText().toString().toLowerCase();
                form_validated=form_validation();
                if(form_validated){
                    Bundle enext = new Bundle();
                    enext.putString("email",email);

                    new_password=((MainActivity)getActivity()).generateRandomPassword();
                    Toast.makeText(getContext(), new_password,Toast.LENGTH_LONG).show();

                    ((MainActivity)getActivity()).imageresize(0.32f);
                    ((MainActivity)getActivity()).reloadimg(R.drawable.forgot_password);
                    ((MainActivity)getActivity()).makefragmentbig(0.77f);
                    FragmentManager m=getFragmentManager();
                    FragmentTransaction ft=m.beginTransaction();
                    Fragment chg_pswd =  new ChangePasswordFragment();
                    chg_pswd.setArguments(enext);
                    ft.replace(R.id.RegistrationFrame,chg_pswd);
                    ft.commit();

                }else{
                    Toast.makeText(getContext(), "Enter Valid Email!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    public boolean form_validation(){

        Pattern email_regex = Patterns.EMAIL_ADDRESS;
        Matcher email_match = email_regex.matcher(email);


        boolean form_valid=true;

        if(!email_match.matches()) {
            edit_reset_pass_email.setError("Please enter a valid email id");
            form_valid=false;
        }

        return form_valid;
    }
}