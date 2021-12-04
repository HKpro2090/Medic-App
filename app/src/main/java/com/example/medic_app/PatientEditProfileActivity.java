package com.example.medic_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

public class PatientEditProfileActivity extends AppCompatActivity {
    String name,gender,bldgrp,phno,email;
    String age,pht,pweit;
    ImageView dp;
    int SELECT_PICTURE = 200;
    int patientdp=R.drawable.patient2;
    EditText pname,page,pgen,pbgrp,pheight,pweight,pemail,pphno;
    Spinner pg,pb;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference user_col = db.collection("users");
    String[] bloodgroups = { "A+", "A-",
            "B+", "B-",
            "O+", "O-","AB-","AB+" };
    String[] genders = {"Male","Female","Prefer not to Say"};

    ArrayList<String> epbg = new ArrayList<>();
    ArrayList<String> epgender = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_edit_profile);

        Intent in = getIntent();
        email=in.getStringExtra("email");

        dp=(ImageView) findViewById(R.id.PatientEditDP);
        dp.setImageResource(patientdp);
        pname=(EditText) findViewById(R.id.patientProfileName);
        page=(EditText) findViewById(R.id.patientProfileAge);
        pgen=(EditText) findViewById(R.id.patientProfileGender);
        pbgrp=(EditText) findViewById(R.id.patientProfileBldGrp);
        pphno=(EditText) findViewById(R.id.patientProfilePhno);
        pemail=(EditText) findViewById(R.id.patientProfileEmail);
        pheight=(EditText) findViewById(R.id.patientProfileheight);
        pweight=(EditText) findViewById(R.id.patientProfileweight);

        ImageButton editname=(ImageButton) findViewById(R.id.editPname);
        ImageButton editage=(ImageButton) findViewById(R.id.editPAge);
        ImageButton editgender=(ImageButton) findViewById(R.id.editPgender);
        ImageButton editbgrp=(ImageButton) findViewById(R.id.editPbldgrp);
        ImageButton editphno=(ImageButton) findViewById(R.id.editPPhno);
        ImageButton editHeight=(ImageButton)findViewById(R.id.editPheight);
        ImageButton editWeight=(ImageButton)findViewById(R.id.editPWeight);

        pg=(Spinner) findViewById(R.id.PatientEditProfileGenderspinner);
        pb=(Spinner) findViewById(R.id.PatientEditProfileBgrpspinner);
        ArrayAdapter<String> adp=new ArrayAdapter<String>(this,R.layout.my_selected_item,genders);
        ArrayAdapter<String> ad=new ArrayAdapter<String>(this,R.layout.my_selected_item,bloodgroups);


        user_col.document("user_"+email).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if(task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();
                    if(document.exists()){

                        name=document.getString("user_name_key");
                        age=document.getString("age_key");
                        gender=document.getString("gender_key");
                        bldgrp=document.getString("blood_group_key");
                        phno=document.getString("phone_no_key");
                        pht=document.getString("height_key");
                        pweit=document.getString("weight_key");


                        pname.setText(name);
                        page.setText(age);
                        pgen.setText(gender);
                        pbgrp.setText(bldgrp);
                        pphno.setText(phno);
                        pemail.setText(email);
                        pheight.setText(pht);
                        pweight.setText(pweit);

                        pg.setAdapter(adp);

                        pb.setAdapter(ad);
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
                pname.setEnabled(true);
                pname.setInputType(InputType.TYPE_CLASS_TEXT);
            }
        });
        editage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                page.setEnabled(true);
                page.setInputType(InputType.TYPE_CLASS_TEXT);

            }
        });
        editgender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                pgen.setEnabled(true);
//                pgen.setInputType(InputType.TYPE_CLASS_TEXT);
                pgen.setVisibility(View.INVISIBLE);
                pg.setVisibility(View.VISIBLE);
                pg.setEnabled(true);
            }
        });
        editbgrp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                pbgrp.setEnabled(true);
//                pbgrp.setInputType(InputType.TYPE_CLASS_TEXT);
                pbgrp.setVisibility(View.INVISIBLE);
                pb.setVisibility(View.VISIBLE);
                pb.setEnabled(true);
            }
        });
        editphno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pphno.setEnabled(true);
                pphno.setInputType(InputType.TYPE_CLASS_TEXT);
            }
        });
        editHeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pheight.setEnabled(true);
                pheight.setInputType(InputType.TYPE_CLASS_TEXT);
            }
        });
        editWeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pweight.setEnabled(true);
                pweight.setInputType(InputType.TYPE_CLASS_TEXT);
            }
        });
//        editemail.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                pemail.setEnabled(true);
//                pemail.setInputType(InputType.TYPE_CLASS_TEXT);
//            }
//        });

        FloatingActionButton changedp=(FloatingActionButton)findViewById(R.id.floatingActionButton);
        changedp.bringToFront();
        changedp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
                Toast.makeText(getApplicationContext(),"button works",Toast.LENGTH_LONG).show();
            }
        });

        pg.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                gender=genders[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        pb.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                bldgrp=bloodgroups[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        FloatingActionButton updt=(FloatingActionButton) findViewById(R.id.editProfileUpdateButton2);
        updt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name=pname.getText().toString();
                age=page.getText().toString();
                phno=pphno.getText().toString();
                pht=pheight.getText().toString();
                pweit=pweight.getText().toString();
                pgen.setVisibility(View.VISIBLE);
                pgen.setText(gender);
                pbgrp.setVisibility(View.VISIBLE);
                pbgrp.setText(bldgrp);
                Map<String,Object> edit_details = new Hashtable<>();
                edit_details.put("user_name_key",name);
                edit_details.put("age_key",age);
                edit_details.put("height_key",pht);
                edit_details.put("weight_key",pweit);
                edit_details.put("blood_group_key",bldgrp);
                edit_details.put("gender_key",gender);
                edit_details.put("phone_no_key",phno);

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
        pname.setEnabled(false);
        pname.setInputType(InputType.TYPE_NULL);
        pg.setVisibility(View.GONE);
        pg.setEnabled(false);
        pb.setVisibility(View.GONE);
        pb.setEnabled(false);
        page.setEnabled(false);
        page.setInputType(InputType.TYPE_NULL);
        pgen.setEnabled(false);
        pgen.setInputType(InputType.TYPE_NULL);
        pbgrp.setEnabled(false);
        pbgrp.setInputType(InputType.TYPE_NULL);
        pphno.setEnabled(false);
        pphno.setInputType(InputType.TYPE_NULL);
        pheight.setEnabled(false);
        pheight.setInputType(InputType.TYPE_NULL);
        pweight.setEnabled(false);
        pweight.setInputType(InputType.TYPE_NULL);
        pemail.setEnabled(false);
        pemail.setInputType(InputType.TYPE_NULL);
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