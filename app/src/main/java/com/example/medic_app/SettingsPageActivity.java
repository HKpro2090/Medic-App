package com.example.medic_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class SettingsPageActivity extends AppCompatActivity {

    String user_name,email="";
    String user_name_key="user_name_key";
    String e_key="email";
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference user_col = db.collection("users");
    TextView user_text;
    SwitchCompat dmode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_page);

        Intent intent = getIntent();
        email = intent.getStringExtra(e_key);

        ImageView editprofile=(ImageView)findViewById(R.id.imageView3);
        editprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),PatientEditProfileActivity.class);
                i.putExtra(e_key,email);
                startActivity(i);
            }
        });





        TextView logout=(TextView) findViewById(R.id.textview9);
        user_text = (TextView) findViewById(R.id.profilepagename);

        DocumentReference user_doc = user_col.document("user_"+email);
        user_doc.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        user_name = document.getData().get(user_name_key).toString();
                        user_text.setText(user_name);
                    }
                }
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutAlert();
            }
        });

        TextView changepass=(TextView) findViewById(R.id.textview8);
        changepass.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent in=new Intent(getApplicationContext(),ProfileChangePasswordActivity.class);
                in.putExtra(e_key,email);
                in.putExtra(user_name_key,user_name);
                startActivity(in);

//                Intent in=new Intent(getApplicationContext(),MainActivity.class);
//                in.putExtra("changepass","yes");
//                startActivity(in);

            }
        });

        TextView aboutus = (TextView) findViewById(R.id.aboutustv);
        aboutus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(),AboutUsActivity.class);
                startActivity(in);
            }
        });

        dmode=(SwitchCompat) findViewById(R.id.switch1);
//        dmode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (isChecked) {
//                    // The toggle is enabled
//                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
//                } else {
//                    // The toggle is disabled
//                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
//                }
//            }
//        });

    }
    private void logoutAlert(){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Confirm");
        builder.setMessage("Are you sure you want to logout?");
        builder.setCancelable(false);
        builder.setIcon(R.drawable.ic_baseline_exit_to_app_48);
        builder.setPositiveButton("logout", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent in=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(in);
                finishAffinity();
                Toast.makeText(getApplicationContext(),"Logout Successfull!",Toast.LENGTH_LONG).show();
            }
        });
        builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alert=builder.create();
        alert.show();
    }
}