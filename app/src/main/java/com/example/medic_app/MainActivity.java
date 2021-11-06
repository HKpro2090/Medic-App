package com.example.medic_app;

import android.content.res.Resources;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       Bundle users = new Bundle();
       users.putString("ajiteshjs2001@gmail.com","");
       loadFragment(new LoginFragment());

    }

    public void loadFragment(Fragment fragment)
    {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.RegistrationFrame, fragment);
        fragmentTransaction.commit();
    }

    public void loadtoast()
    {
        Toast.makeText(getApplicationContext(),"Signing in",Toast.LENGTH_LONG).show();
        System.out.println("Clicked");
    }
}