package com.example.medic_app;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.common.hash.Hashing;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChangePasswordFragment extends Fragment {

    String email="";
    String e_key="email";
    String settings_key = "from_settings";
    EditText edit_email,edit_password,edit_conf_passwd,edit_cur_password;
    String cur_password,password,conf_passwd,encrypted_password,encrypted_cur_password,db_password="";
    boolean form_validated = false;
    boolean user_exists_check = false;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference user_col = db.collection("users");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.change_password_fragment_layout, container, false);

        Button cancel=(Button)view.findViewById(R.id.CancelChangePasswordButton);
        Button change =(Button)view.findViewById(R.id.ChangePasswordButton);

        edit_email = (EditText) view.findViewById(R.id.EmailAddressField);
        edit_cur_password = (EditText) view.findViewById(R.id.CurrentPasswordField);
        edit_password = (EditText) view.findViewById(R.id.NewPasswordField);
        edit_conf_passwd = (EditText) view.findViewById(R.id.ConfirmNewPasswordField);

        Bundle b = getArguments();
        email = b.getString(e_key);
        edit_email.setText(email);


        change.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                try {

                    email = edit_email.getText().toString().toLowerCase();
                    cur_password = edit_cur_password.getText().toString();
                    password = edit_password.getText().toString();
                    conf_passwd = edit_conf_passwd.getText().toString();

                    form_validated=form_validation();

                    if(form_validated) {

                        DocumentReference user_doc = user_col.document("user_"+email);
                        user_doc.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot document = task.getResult();
                                    if (document.exists()) {
                                        user_exists_check=true;
                                        try {
                                            encrypted_cur_password = encrypt_passwd(cur_password);
                                        } catch (Error e) {
                                            Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_LONG).show();
                                        }

                                        db_password = document.getData().get("password_key").toString();

                                        if(db_password.matches(encrypted_cur_password)){

                                            try {
                                                encrypted_password = encrypt_passwd(password);
                                            } catch (Error e) {
                                                Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_LONG).show();
                                            }

                                            user_doc.update("password_key", encrypted_password)
                                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void unused) {

                                                            if (b.getString(settings_key)!=null){

                                                                Intent in=new Intent(getContext(),MainActivity.class);
                                                                startActivity(in);
                                                                getActivity().finishAffinity();
                                                            }else{
                                                            Bundle email_carrier = new Bundle();
                                                            email_carrier.putString(e_key,email);

                                                            Toast.makeText(getActivity(), "Password Changed Successfully!", Toast.LENGTH_SHORT).show();
                                                            ((MainActivity) getActivity()).imageresize(0.32f);
                                                            ((MainActivity) getActivity()).reloadimg(R.drawable.loginpage);
                                                            ((MainActivity) getActivity()).makefragmentbig(0.54f);
                                                            FragmentManager m = getFragmentManager();
                                                            FragmentTransaction ft = m.beginTransaction();
                                                            ft.setCustomAnimations(R.anim.fade_in,R.anim.fade_out);
                                                            Fragment login_fgmt = new LoginFragment();
                                                            login_fgmt.setArguments(email_carrier);

                                                            ft.replace(R.id.RegistrationFrame, login_fgmt);
                                                            ft.commit();
                                                            }
                                                        }

                                                    })
                                                    .addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception e) {

                                                            Toast.makeText(getActivity(), "Password Change Failed! Firebase Connection Issue!", Toast.LENGTH_SHORT).show();

                                                        }
                                                    });

                                        }else {

                                            edit_cur_password.setError("Incorrect Existing Password! Please reset or try again!");
                                            Toast.makeText(getActivity(), "Wrong Current Password!", Toast.LENGTH_SHORT).show();
                                        }
                                    } else {

                                        user_exists_check=false;
                                        edit_email.setError("User doesn't exist, sign up or enter registered email id.");
                                        Toast.makeText(getContext(),  "User Doesn't Exist!", Toast.LENGTH_LONG).show();

                                    }
                                } else {
                                    Toast.makeText(getContext(), task.getException().toString()+" FireBase Connection ERROR!", Toast.LENGTH_LONG).show();
                                }
                            }
                        });


                    }else{
                        Toast.makeText(getContext(), "Enter Valid Details!", Toast.LENGTH_LONG).show();

                    }

                }catch (Exception er){

                    Toast.makeText(getContext(), er.toString(), Toast.LENGTH_LONG).show();

                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                if (b.getString(settings_key)!=null){
//                    Intent patient_profile_page = new Intent(getContext(), SettingsPageActivity.class);
//                    patient_profile_page.putExtra(e_key,email);
//                    startActivity(patient_profile_page);
                    getActivity().finish();
                }
                else {
                    ((MainActivity) getActivity()).imageresize(0.32f);
                    ((MainActivity) getActivity()).reloadimg(R.drawable.loginpage);
                    ((MainActivity) getActivity()).makefragmentbig(0.54f);

                    FragmentManager m = getFragmentManager();
                    FragmentTransaction ft = m.beginTransaction();
                    ft.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
                    ft.replace(R.id.RegistrationFrame, new LoginFragment());
                    ft.commit();
                }
            }
        });

        return view;
    }

    public String encrypt_passwd(String passwd) {

        String encrypted_passwd="";

        encrypted_passwd = Hashing.sha256()
                .hashString(passwd, StandardCharsets.UTF_8)
                .toString();

        return encrypted_passwd;
    }

    public boolean form_validation(){

        Pattern email_regex = Patterns.EMAIL_ADDRESS;
        Pattern password_regex = Pattern.compile("^(?=.*[0-9]+)(?=.*[a-z]+)(?=.*[A-Z]+)(?=.*[!@#$%^&*+=-]+).{8,32}$");
        Matcher email_match = email_regex.matcher(email);
        Matcher password_match = password_regex.matcher(password);
        Matcher cur_password_match = password_regex.matcher(cur_password);
        boolean form_valid=true;

        if(!email_match.matches()) {
            edit_email.setError("Please enter a valid email id");
            form_valid=false;
        }
        if(!password_match.matches()){
            edit_password.setError("Please enter a valid password containing at least 1 lower case, 1 upper case, 1 number and 1 special character [!@#$%^&*+=-]. Minimum Length must be 8 characters");
            form_valid=false;
        }

        if(!cur_password_match.matches()){
            edit_cur_password.setError("Please enter a valid password containing at least 1 lower case, 1 upper case, 1 number and 1 special character [!@#$%^&*+=-]. Minimum Length must be 8 characters");
            form_valid=false;
        }
        if(!(password.equals(conf_passwd))){
            edit_conf_passwd.setError("Password doesn't match!");
            form_valid=false;

        }

        return form_valid;
    }
}