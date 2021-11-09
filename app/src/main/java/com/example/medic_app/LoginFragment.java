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
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.security.NoSuchAlgorithmException;
import java.util.Hashtable;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginFragment extends Fragment {

    EditText edit_email,edit_password;
    String email,password,user_type,encrypted_password_login;
    String db_password,db_usertype;
    RadioButton doctor_btn,patient_btn;
    boolean form_validated = false;
    boolean user_exists_check = false;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference user_col = db.collection("users");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.login_fragment_layout, container, false);
        // Inflate the layout for this fragment

        edit_email = (EditText) view.findViewById(R.id.EmailField);
        edit_password = (EditText) view.findViewById(R.id.PasswordField);
        doctor_btn = (RadioButton) view.findViewById(R.id.DoctorRadioButton);
        patient_btn = (RadioButton) view.findViewById(R.id.PatientRadioButton);

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
               public void onClick(View view) {

                   try{

                       email= edit_email.getText().toString();
                       password = edit_password.getText().toString();

                       if (doctor_btn.isChecked()) {
                           user_type = "Doctor";
                       } else if (patient_btn.isChecked()) {
                           user_type = "Patient";
                       }

                       form_validated=form_validation();

                       if(form_validated){

                           try {
                               encrypted_password_login = ((MainActivity)getActivity()).encrypt_passwd(password);
                           } catch (Error e) {
                               Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_LONG).show();
                           }

                           DocumentReference user_doc = user_col.document("user_"+email);
                           user_doc.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                               @Override
                               public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                   if (task.isSuccessful()) {
                                       DocumentSnapshot document = task.getResult();

                                       if (document.exists()) {
                                           user_exists_check=true;


                                           db_password = document.getData().get("password_key").toString();
                                           db_usertype = document.getData().get("user_type_key").toString();


                                           if(db_password.matches(encrypted_password_login)) {
                                               Toast.makeText(getActivity(), "Login Successful!", Toast.LENGTH_SHORT).show();

                                               if(user_type.matches("Patient")){
                                                    Intent patient_home = new Intent(getContext(), PatientHomePageActivity.class);
                                                    patient_home.putExtra("email", email);
                                                    startActivity(patient_home);
                                                } else if(user_type.matches("Doctor")) {
                                                   Intent doctor_home = new Intent(getContext(), PatientHomePageActivity.class);     //Change Doctor Page Here
                                                   doctor_home.putExtra("email", email);
                                                   startActivity(doctor_home);
                                               }

                                           }else{

                                               edit_password.setError("Invalid Credentials! Try Again or reset password!.");
                                           }
                                       } else {
                                           user_exists_check=false;
                                           edit_email.setError("User doesn't exits! Sign up or retry with registered email id.");
                                           Toast.makeText(getActivity(), "User Doesn't Exits!", Toast.LENGTH_SHORT).show();

                                       }
                                   } else {
                                       Toast.makeText(getContext()," FireBase Connection ERROR!", Toast.LENGTH_LONG).show();
                                   }
                               }
                           });

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

    public boolean form_validation(){

        Pattern email_regex = Patterns.EMAIL_ADDRESS;
        Pattern password_regex = Pattern.compile("^(?=.*[0-9]+)(?=.*[a-z]+)(?=.*[A-Z]+)(?=.*[!@#$%^&*+=-]+).{8,32}$");

        Matcher email_match = email_regex.matcher(email);
        Matcher password_match = password_regex.matcher(password);

        boolean form_valid=true;

        if(!email_match.matches()) {
            edit_email.setError("Please enter a valid email id");
            form_valid=false;
        }
        if(!password_match.matches()){
            edit_password.setError("Please enter a valid password containing at least 1 lower case, 1 upper case, 1 number and 1 special character [!@#$%^&*+=-]. Minimum Length must be 8 characters");
            form_valid=false;
        }

        return form_valid;
    }


}