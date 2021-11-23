package com.example.medic_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ProfilePageActivity extends AppCompatActivity {

    String user_name,email="";
    String user_name_key="user_name_key";
    String e_key="email";
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference user_col = db.collection("users");
    TextView user_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);

        Intent intent = getIntent();
        email = intent.getStringExtra(e_key);

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
                Intent in=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(in);
                finishAffinity();
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
    }
}