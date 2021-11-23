package com.example.medic_app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


public class ProfileChangePasswordActivity extends AppCompatActivity {

    String user_name_key="user_name_key";
    String e_key="email";
    String email,user_name="";
    String from_settings="Yes";
    String settings_key = "from_settings";
    TextView user_text;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_change_password);

        Intent intent = getIntent();
        email = intent.getStringExtra(e_key);
        user_name = intent.getStringExtra(user_name_key);

        user_text = (TextView)findViewById(R.id.change_password_user_name);
        user_text.setText(user_name);

        Bundle email_next = new Bundle();
        email_next.putString(e_key,email);
        email_next.putString(settings_key,from_settings);

        Fragment chg_pswd =  new ChangePasswordFragment();
        chg_pswd.setArguments(email_next);

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.SettingsFrame, chg_pswd);
        fragmentTransaction.commit();
    }
}