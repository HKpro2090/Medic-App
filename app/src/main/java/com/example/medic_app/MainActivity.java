package com.example.medic_app;

import android.app.ActionBar;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Button;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.common.hash.Hashing;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       loadFragment(new LoginFragment());
    }
    public void loadFragment(Fragment fragment)
    {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.RegistrationFrame, fragment);
        fragmentTransaction.commit();
    }

    public void imageresize(float f){
        ConstraintSet set  = new ConstraintSet();
        ImageView imv = (ImageView)findViewById(R.id.imageView);
        ViewGroup.LayoutParams lp = (ConstraintLayout.LayoutParams)imv.getLayoutParams();
        ((ConstraintLayout.LayoutParams) lp).matchConstraintPercentHeight = f;
    }

    public void makeimgempty()
    {
        ImageView imv = (ImageView) findViewById(R.id.imageView);
        imv.setImageResource(0);
    }
    public void makefragmentbig(float f)
    {
        ConstraintSet set  = new ConstraintSet();
        FragmentContainerView imv = (FragmentContainerView) findViewById(R.id.RegistrationFrame);
        ViewGroup.LayoutParams lp = (ConstraintLayout.LayoutParams)imv.getLayoutParams();
        ((ConstraintLayout.LayoutParams) lp).matchConstraintPercentHeight = f;
    }

    public void reloadimg(int img)
    {
        ImageView imv = (ImageView) findViewById(R.id.imageView);
        imv.setImageResource(img);
    }

    public String encrypt_passwd(String passwd) {

        String encrypted_passwd="";

        encrypted_passwd = Hashing.sha256()
                .hashString(passwd, StandardCharsets.UTF_8)
                .toString();

        return encrypted_passwd;
    }

    public String generateRandomPassword(){

        String random_password="";
        String alphabetsLow = "abcdefghijklmnopqrstuvwxyz";
        String alphabetsHigh = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String numbers = "0123456789";
        String special = "!@#$%^&*+=-";
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*+=-";

        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder();

        sb.append(alphabetsHigh.charAt(random.nextInt(alphabetsHigh.length())));
        sb.append(alphabetsLow.charAt(random.nextInt(alphabetsLow.length())));
        sb.append(special.charAt(random.nextInt(special.length())));
        sb.append(numbers.charAt(random.nextInt(numbers.length())));


        for (int i = 4; i < 8; i++)
        {
            int randomIndex = random.nextInt(chars.length());
            sb.append(chars.charAt(randomIndex));
        }

        random_password=sb.toString();
        return random_password;

    }
}