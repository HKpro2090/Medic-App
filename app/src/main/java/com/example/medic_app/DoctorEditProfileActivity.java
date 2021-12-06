package com.example.medic_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

public class DoctorEditProfileActivity extends AppCompatActivity {

    String name,gender,department,phone,email,age,qualification,about;
    ImageView dp;
    int SELECT_PICTURE = 200;
    int patientdp=R.drawable.patient2;
    EditText dname,dage,dqual,dphno,demail,dabout,dgen,ddept;
    Spinner dg,dd;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference user_col = db.collection("users");

    String [] dept={"Orthopaedic","ENT","Cardio","Pediatrician","General Physician"};
    String[] genders = {"Male","Female","Prefer not to Say"};

    ArrayList<String> eddp = new ArrayList<>();
    ArrayList<String> edgender = new ArrayList<>();

    SharedPreferences shp;
    SharedPreferences.Editor ed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_edit_profile);

        //Intent in = getIntent();
        shp = getSharedPreferences("sp", Context.MODE_PRIVATE);
        email = shp.getString("username","");
        dp=(ImageView) findViewById(R.id.doctorEditDP);
        dp.setImageResource(patientdp);
        dname=(EditText) findViewById(R.id.doctorProfileName);
        dage=(EditText) findViewById(R.id.doctorProfileAge);
        dgen=(EditText) findViewById(R.id.doctorProfileGender);
        ddept=(EditText) findViewById(R.id.doctorprofiledept);
        dphno=(EditText) findViewById(R.id.doctorPhoneNumber);
        demail=(EditText) findViewById(R.id.doctorprofileemail);
        dqual=(EditText) findViewById(R.id.doctorProfilequalification);
        dabout=(EditText) findViewById(R.id.doctorProfileAbout);

        ImageButton editname=(ImageButton) findViewById(R.id.editDname);
        ImageButton editage=(ImageButton) findViewById(R.id.editDAge);
        ImageButton editgender=(ImageButton) findViewById(R.id.editDgender);
        ImageButton editdept=(ImageButton) findViewById(R.id.editDdept);
        ImageButton editphno=(ImageButton) findViewById(R.id.editDphone);
        ImageButton editQual=(ImageButton)findViewById(R.id.editDqual);
        ImageButton editAbout=(ImageButton)findViewById(R.id.editDabout);

        dg=(Spinner) findViewById(R.id.PatientEditDoctorProfileGenderspinner);
        dd=(Spinner) findViewById(R.id.DoctorEditProfileQulaificationSpinner);
        ArrayAdapter<String> adp=new ArrayAdapter<String>(this,R.layout.my_selected_item,genders);
        ArrayAdapter<String> ad=new ArrayAdapter<String>(this,R.layout.my_selected_item,dept);


        user_col.document("user_"+email).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if(task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();
                    if(document.exists()){

                        name=document.getString("user_name_key");
                        age=document.getString("age_key");
                        gender=document.getString("gender_key");
                        department=document.getString("department_key");
                        phone=document.getString("phone_no_key");
                        about=document.getString("about_key");
                        qualification=document.getString("Qualification_key");


                        dname.setText(name);
                        dage.setText(age);
                        dgen.setText(gender);
                        ddept.setText(department);
                        dphno.setText(phone);
                        demail.setText(email);
                        dabout.setText(about);
                        dqual.setText(qualification);

                        dg.setAdapter(adp);

                        dd.setAdapter(ad);
                        nonEditable();

                    }else{
                        Toast.makeText(getApplicationContext(), "User Not Found!", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "FireBase Error!", Toast.LENGTH_SHORT).show();
                }

            }
        });
        //Placeholder values



//        ImageButton editemail=(ImageButton) findViewById(R.id.editPemail);

        editname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dname.setEnabled(true);
                dname.setInputType(InputType.TYPE_CLASS_TEXT);
            }
        });
        editage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dage.setEnabled(true);
                dage.setInputType(InputType.TYPE_CLASS_TEXT);

            }
        });
        editgender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                pgen.setEnabled(true);
//                pgen.setInputType(InputType.TYPE_CLASS_TEXT);
                dgen.setVisibility(View.INVISIBLE);
                dg.setVisibility(View.VISIBLE);
                dg.setEnabled(true);
            }
        });
        editdept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                pbgrp.setEnabled(true);
//                pbgrp.setInputType(InputType.TYPE_CLASS_TEXT);
                ddept.setVisibility(View.INVISIBLE);
                dd.setVisibility(View.VISIBLE);
                dd.setEnabled(true);
            }
        });
        editphno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dphno.setEnabled(true);
                dphno.setInputType(InputType.TYPE_CLASS_TEXT);
            }
        });
        editQual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dqual.setEnabled(true);
                dqual.setInputType(InputType.TYPE_CLASS_TEXT);
            }
        });
        editAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dabout.setEnabled(true);
                dabout.setInputType(InputType.TYPE_CLASS_TEXT);
            }
        });
//        editemail.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                pemail.setEnabled(true);
//                pemail.setInputType(InputType.TYPE_CLASS_TEXT);
//            }
//        });

        FloatingActionButton changedp=(FloatingActionButton)findViewById(R.id.floatingActionButton2);
        changedp.bringToFront();
        changedp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
//                Toast.makeText(getApplicationContext(),"button works",Toast.LENGTH_LONG).show();
            }
        });

        dg.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                gender=genders[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        dd.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                department=dept[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        FloatingActionButton updt=(FloatingActionButton) findViewById(R.id.editDoctorProfileUpdateButton);
        updt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name=dname.getText().toString();
                age=dage.getText().toString();
                phone=dphno.getText().toString();
                about=dabout.getText().toString();
                qualification=dqual.getText().toString();
                dgen.setVisibility(View.VISIBLE);
                dgen.setText(gender);
                ddept.setVisibility(View.VISIBLE);
                ddept.setText(department);

                Map<String,Object> edit_details = new Hashtable<>();
                edit_details.put("user_name_key",name);
                edit_details.put("age_key",age);
                edit_details.put("about_key",about);
                edit_details.put("Qualification_key",qualification);
                edit_details.put("department_key",department);
                edit_details.put("gender_key",gender);
                edit_details.put("phone_no_key",phone);

                user_col.document("user_"+email).set(edit_details, SetOptions.merge()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(getApplicationContext(), "User Details Updated Successfully!", Toast.LENGTH_SHORT).show();
                    }
                });
                //String disp="Name:"+name+"\nage:"+age+"\ngender:"+gender+"\nbldgrp:"+bldgrp+"\nheight:"+pht+"\nweight:"+pweit+"\nphno:"+phno+"\nemail:"+email;
                //Toast.makeText(getApplicationContext(),disp,Toast.LENGTH_LONG).show();
                nonEditable();
            }
        });
    }

    private void selectImage() {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);

        // pass the constant to compare it
        // with the returned requestCode
        startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);
    }
    private void nonEditable(){
        dname.setEnabled(false);
        dname.setInputType(InputType.TYPE_NULL);
        dg.setVisibility(View.GONE);
        dg.setEnabled(false);
        dd.setVisibility(View.GONE);
        dd.setEnabled(false);
        dage.setEnabled(false);
        dage.setInputType(InputType.TYPE_NULL);
        dgen.setEnabled(false);
        dgen.setInputType(InputType.TYPE_NULL);
        ddept.setEnabled(false);
        ddept.setInputType(InputType.TYPE_NULL);
        dphno.setEnabled(false);
        dphno.setInputType(InputType.TYPE_NULL);
        dabout.setEnabled(false);
        dabout.setInputType(InputType.TYPE_NULL);
        dqual.setEnabled(false);
        dqual.setInputType(InputType.TYPE_NULL);
        demail.setEnabled(false);
        demail.setInputType(InputType.TYPE_NULL);
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            // compare the resultCode with the
            // SELECT_PICTURE constant
            if (requestCode == SELECT_PICTURE) {
                // Get the url of the image from data
                Uri selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    // update the preview image in the layout
                    dp.setImageURI(selectedImageUri);
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}