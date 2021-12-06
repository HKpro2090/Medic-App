package com.example.medic_app;

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
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ResetPasswordFragment extends Fragment {

    String user_name,email,new_password,new_password_encrypted,email_to,email_subject,email_body="";
    String e_key="email";
    String from_email="gautamfbdevacc@gmail.com";
    String email_password="Test@1234";
    EditText edit_reset_pass_email;
    boolean form_validated = false;
    boolean user_exists_check = false;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference user_col = db.collection("users");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_reset_password, container, false);
        Button fpassbtn=(Button)view.findViewById(R.id.resetPassEmailButton);
        Button cancelbtn=(Button)view.findViewById(R.id.rpasscancel);
        edit_reset_pass_email = (EditText) view.findViewById(R.id.resetPassEmailField);

        try {
            Bundle b = getArguments();
            email = b.getString(e_key);
            edit_reset_pass_email.setText(email);
        }catch (Exception no_email){
            email="";
        }

        fpassbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try{

                    email = edit_reset_pass_email.getText().toString().toLowerCase();
                    form_validated=form_validation();

                    if(form_validated){

                        DocumentReference user_doc = user_col.document("user_"+email);
                        user_doc.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot document = task.getResult();
                                    if (document.exists()) {
                                        user_exists_check = true;
                                        user_name = document.getData().get("user_name_key").toString();
                                        new_password=((MainActivity)getActivity()).generateRandomPassword();
                                        try {
                                            new_password_encrypted = ((MainActivity)getActivity()).encrypt_passwd(new_password);
                                        } catch (Error e) {
                                            Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_LONG).show();
                                        }
                                        user_doc.update("password_key", new_password_encrypted)
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void unused) {

                                                        try {
                                                            email_to = email;
                                                            email_subject = "Medic-App - Password Reset Request";
                                                            email_body = "Hi " + user_name + ", Your new password is: " + new_password;

                                                                GMailSender sender = new GMailSender();
                                                                sender.execute(from_email,email_password,email_to,email_subject,email_body);

                                                            Toast.makeText(getActivity(),"Password Reset Email Sent Successfully!",Toast.LENGTH_LONG).show();
                                                            } catch (Exception e) {
                                                                Toast.makeText(getActivity(),"Password Reset Email Sending Failed!"+e.toString(),Toast.LENGTH_LONG).show();
                                                            }


                                                            ((MainActivity)getActivity()).imageresize(0.32f);
                                                            //((MainActivity)getActivity()).reloadimg(R.drawable.forgot_password);
                                                            ((MainActivity)getActivity()).makefragmentbig(0.77f);
                                                            FragmentManager m=getFragmentManager();
                                                            FragmentTransaction ft=m.beginTransaction();
                                                        ft.setCustomAnimations(R.anim.fade_in,R.anim.fade_out);

                                                            Bundle enext = new Bundle();
                                                            enext.putString(e_key,email);

                                                            Fragment chg_pswd =  new ChangePasswordFragment();
                                                            chg_pswd.setArguments(enext);
                                                            ft.replace(R.id.RegistrationFrame,chg_pswd);
                                                            ft.commit();




                                                    }

                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Toast.makeText(getActivity(), "Password Reset Failed! Firebase Connection Issue!", Toast.LENGTH_SHORT).show();

                                                    }
                                                });
                                    } else {
                                        user_exists_check = false;
                                        edit_reset_pass_email.setError("User doesn't exist, sign up or enter registered email id.");
                                        Toast.makeText(getContext(),  "User Doesn't Exist!", Toast.LENGTH_LONG).show();
                                    }
                                } else {
                                    Toast.makeText(getContext(), task.getException().toString()+" FireBase Connection ERROR!", Toast.LENGTH_LONG).show();
                                }
                            }
                        });



                        //Toast.makeText(getContext(), new_password,Toast.LENGTH_LONG).show();


                    }else{
                        Toast.makeText(getContext(), "Enter Valid Email!", Toast.LENGTH_SHORT).show();
                    }
                }catch(Exception err){
                    Toast.makeText(getContext(), err.toString(), Toast.LENGTH_LONG).show();
                }
            }
        });

        cancelbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //((MainActivity)getActivity()).imageresize(0.32f);
                //((MainActivity)getActivity()).animationonoff(false);

                ((MainActivity)getActivity()).reloadimg(R.raw.dcoa);
                ((MainActivity)getActivity()).animationonoff(true);
                ((MainActivity)getActivity()).makefragmentbig(0.54f);
                FragmentManager m=getFragmentManager();
                FragmentTransaction ft=m.beginTransaction();
                ft.setCustomAnimations(R.anim.fade_in,R.anim.fade_out);
                ft.replace(R.id.RegistrationFrame,new LoginFragment());
                ft.commit();
            }
        });

        return view;
    }

    public boolean form_validation(){

        Pattern email_regex = Patterns.EMAIL_ADDRESS;
        Matcher email_match = email_regex.matcher(email);



        boolean form_valid=true;

        if(!email_match.matches()) {
            edit_reset_pass_email.setError("Please enter a valid email id " + email);
            form_valid=false;
        }

        return form_valid;
    }
}