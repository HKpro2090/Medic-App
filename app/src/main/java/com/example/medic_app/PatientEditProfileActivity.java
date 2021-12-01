package com.example.medic_app;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class PatientEditProfileActivity extends AppCompatActivity {
    String name,gender,bldgrp,phno,email;
    int age;
    ImageView dp;
    int SELECT_PICTURE = 200;
    int patientdp=R.drawable.patient2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_edit_profile);
        //Placeholder values
        name="abcd";
        age=20;
        gender="male";
        bldgrp="O+";
        phno="9192345536";
        email="tst@1234.com";

        dp=(ImageView) findViewById(R.id.PatientEditDP);
        dp.setImageResource(patientdp);
        EditText pname=(EditText) findViewById(R.id.patientProfileName);
        EditText page=(EditText) findViewById(R.id.patientProfileAge);
        EditText pgen=(EditText) findViewById(R.id.patientProfileGender);
        EditText pbgrp=(EditText) findViewById(R.id.patientProfileBldGrp);
        EditText pphno=(EditText) findViewById(R.id.patientProfilePhno);
        EditText pemail=(EditText) findViewById(R.id.patientProfileEmail);
        pname.setText(name);
        page.setText(Integer.toString(age));
        pgen.setText(gender);
        pbgrp.setText(bldgrp);
        pphno.setText(phno);
        pemail.setText(email);

        ImageButton editname=(ImageButton) findViewById(R.id.editPname);
        ImageButton editage=(ImageButton) findViewById(R.id.editPAge);
        ImageButton editgender=(ImageButton) findViewById(R.id.editPgender);
        ImageButton editbgrp=(ImageButton) findViewById(R.id.editPbldgrp);
        ImageButton editphno=(ImageButton) findViewById(R.id.editPPhno);
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
                pgen.setEnabled(true);
                pgen.setInputType(InputType.TYPE_CLASS_TEXT);
            }
        });
        editbgrp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pbgrp.setEnabled(true);
                pbgrp.setInputType(InputType.TYPE_CLASS_TEXT);
            }
        });
        editphno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pphno.setEnabled(true);
                pphno.setInputType(InputType.TYPE_CLASS_TEXT);
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


        FloatingActionButton updt=(FloatingActionButton) findViewById(R.id.editProfileUpdateButton2);
        updt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name=pname.getText().toString();
                age=Integer.parseInt(page.getText().toString());
                gender=pgen.getText().toString();
                bldgrp=pbgrp.getText().toString();
                phno=pphno.getText().toString();
                email=pemail.getText().toString();
                String disp="Name:"+name+"\nage:"+age+"\ngender:"+gender+"\nbldgrp:"+bldgrp+"\nphno:"+phno+"\nemail:"+email;
                Toast.makeText(getApplicationContext(),disp,Toast.LENGTH_LONG).show();
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