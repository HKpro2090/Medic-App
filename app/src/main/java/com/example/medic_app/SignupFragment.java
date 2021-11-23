package com.example.medic_app;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.security.NoSuchAlgorithmException;
import java.util.Hashtable;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignupFragment extends Fragment {

    EditText edit_name,edit_email,edit_phone_no,edit_password,edit_conf_passwd;
    String name,email,phone_no,password,conf_passwd,user_type,encrypted_password="";
    String e_key="email";
    RadioButton doctor_btn,patient_btn;
    Button signup_btn,signin_btn;
    boolean form_validated = false;
    boolean user_exists_check = true;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference user_col = db.collection("users");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        View view = inflater.inflate(R.layout.signup_fragment_layout, container, false);

        signup_btn=(Button)view.findViewById(R.id.SignupPasswordButton);
        signin_btn=(Button)view.findViewById(R.id.SignInButton);

        edit_name = (EditText) view.findViewById(R.id.NameField);
        edit_email = (EditText) view.findViewById(R.id.EmailAddressField);
        edit_phone_no = (EditText) view.findViewById(R.id.PhoneField);
        edit_password = (EditText) view.findViewById(R.id.PasswordField);
        edit_conf_passwd = (EditText) view.findViewById(R.id.ConfirmPasswordField);

        doctor_btn = (RadioButton) view.findViewById(R.id.DoctorRadioButton);
        patient_btn = (RadioButton) view.findViewById(R.id.PatientRadioButton);

        signin_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).imageresize(0.32f);
                ((MainActivity)getActivity()).reloadimg(R.drawable.loginpage);
                ((MainActivity)getActivity()).makefragmentbig(0.54f);
                FragmentManager m=getFragmentManager();
                FragmentTransaction ft=m.beginTransaction();
                ft.setCustomAnimations(R.anim.fade_in,R.anim.fade_out);
                ft.replace(R.id.RegistrationFrame,new LoginFragment());
                ft.commit();
            }
        });

        signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            //below section for test purpose. remove for final app
            if(doctor_btn.isChecked()){
                FragmentManager m=getFragmentManager();
                FragmentTransaction ft=m.beginTransaction();
                ft.setCustomAnimations(R.anim.fade_in,R.anim.fade_out);

                ft.replace(R.id.RegistrationFrame,new PatientCompleteProfileFragment());
                ft.commit();
            }
            //above section for test purpose. remove for final app

                try {
                    name = edit_name.getText().toString();
                    email = edit_email.getText().toString().toLowerCase();
                    phone_no = edit_phone_no.getText().toString();
                    password = edit_password.getText().toString();
                    conf_passwd = edit_conf_passwd.getText().toString();

                    if (doctor_btn.isChecked()) {
                        user_type = "Doctor";
                    } else if (patient_btn.isChecked()) {
                        user_type = "Patient";
                    }

                    //Toast.makeText(getContext(), "Button Triggered", Toast.LENGTH_SHORT).show();

                    form_validated = form_validation();
                    if(form_validated){
                        //isExistingUser();
                        DocumentReference user_doc = user_col.document("user_"+email);
                        user_doc.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot document = task.getResult();
                                    if (document.exists()) {
                                        user_exists_check=true;
                                        edit_email.setError("User with same email id already exits! Try logging into existing account or use different email id.");
                                        Toast.makeText(getActivity(), "User Already Exits!", Toast.LENGTH_SHORT).show();
                                    } else {
                                        user_exists_check=false;
                                        try {
                                            encrypted_password = ((MainActivity)getActivity()).encrypt_passwd(password);
                                        } catch (Error e) {
                                            Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_LONG).show();

                                        }
                                        Map<String,Object> user_sign_up_details = new Hashtable<>();
                                        user_sign_up_details.put("user_name_key",name);
                                        user_sign_up_details.put("email_id_key",email);
                                        user_sign_up_details.put("phone_no_key",phone_no);
                                        user_sign_up_details.put("password_key",encrypted_password);
                                        user_sign_up_details.put("user_type_key",user_type);
                                        user_col.document("user_"+email).set(user_sign_up_details);
                                        Toast.makeText(getActivity(), "User Created Successfully!", Toast.LENGTH_SHORT).show();

                                        Bundle frag_trans = new Bundle();
                                        frag_trans.putString(e_key,email);

                                        if(user_type.matches("Patient")){
//                                            Intent patient_home = new Intent(getContext(), PatientHomePageActivity.class);
//                                            patient_home.putExtra("email", email);
//                                            startActivity(patient_home);
                                            FragmentManager m=getFragmentManager();
                                            FragmentTransaction ft=m.beginTransaction();
                                            Fragment patient_complete_profile_fragment = new PatientCompleteProfileFragment();
                                            patient_complete_profile_fragment.setArguments(frag_trans);
                                            ft.replace(R.id.RegistrationFrame,patient_complete_profile_fragment);
                                            ft.commit();

                                        } else if(user_type.matches("Doctor")) {
//                                            Intent doctor_home = new Intent(getContext(), PatientHomePageActivity.class);  //Change Doctor Page Here
//                                            doctor_home.putExtra("email", email);
//                                            startActivity(doctor_home);
                                            FragmentManager m=getFragmentManager();
                                            FragmentTransaction ft=m.beginTransaction();
                                            Fragment doctor_complete_profile_fragment = new PatientCompleteProfileFragment();
                                            doctor_complete_profile_fragment.setArguments(frag_trans);
                                            ft.replace(R.id.RegistrationFrame,doctor_complete_profile_fragment);
                                            ft.commit();
                                        }

                                    }
                                } else {
                                    Toast.makeText(getContext(), task.getException().toString()+" FireBase Connection ERROR!", Toast.LENGTH_LONG).show();
                                }
                            }
                        });

                        /* if(!user_exists_check){
                            Toast.makeText(getActivity(), "User Created Successfully!", Toast.LENGTH_SHORT).show();
                            Intent patient_home=new Intent(getContext(), PatientHomePageActivity.class);
                            startActivity(patient_home);}
                        else{
                            edit_email.setError("User with same email id already exits! Try logging into existing account or use different email id.");
                            Toast.makeText(getActivity(), "User Already Exits!", Toast.LENGTH_SHORT).show();
                        }*/

                    }else
                    {
                        Toast.makeText(getContext(), "Enter Valid Details!", Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception err) {

                    Toast.makeText(getContext(), err.toString(), Toast.LENGTH_LONG).show();
                }
            }
        });
        return view;
    }


/*
    public void isExistingUser(){
        Query user_id_exist = user_col.whereEqualTo("email_id_key", email);
        user_id_exist.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            user_exists_check = true;
                        } else {

                            Toast.makeText(getContext(), task.getException().toString()+"FB ERROR", Toast.LENGTH_LONG).show();
                            user_exists_check = false;
                        }
                    }
                });
    }
*/

    public boolean form_validation(){
        Pattern name_regex = Pattern.compile("[a-zA-Z]{3,}");
        Pattern phone_no_regex = Pattern.compile("([1-9][0-9]{9})|^$");
        Pattern email_regex = Patterns.EMAIL_ADDRESS;
        Pattern password_regex = Pattern.compile("^(?=.*[0-9]+)(?=.*[a-z]+)(?=.*[A-Z]+)(?=.*[!@#$%^&*+=-]+).{8,32}$");

        Matcher name_match = name_regex.matcher(name);
        Matcher phone_no_match = phone_no_regex.matcher(phone_no);
        Matcher email_match = email_regex.matcher(email);
        Matcher password_match = password_regex.matcher(password);

        boolean form_valid=true;

         if(!name_match.matches()) {
             edit_name.setError("Please enter a valid name, no spaces");
             form_valid=false;
         }
        if(!phone_no_match.matches()) {
            edit_phone_no.setError("Please enter a valid 10 digit phone no");
            form_valid=false;
        }
        if(!email_match.matches()) {
            edit_email.setError("Please enter a valid email id");
            form_valid=false;
        }
        if(!password_match.matches()){
            edit_password.setError("Please enter a valid password containing at least 1 lower case, 1 upper case, 1 number and 1 special character [!@#$%^&*+=-]. Minimum Length must be 8 characters");
            form_valid=false;
        }
        if(!(password.equals(conf_passwd))){
            edit_conf_passwd.setError("Password doesn't match!");
            form_valid=false;

        }

        return form_valid;
    }
}