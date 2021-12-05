package com.example.medic_app;

import android.animation.ValueAnimator;
import android.app.ActionBar;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.transition.TransitionManager;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
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

import com.airbnb.lottie.LottieAnimationView;
import com.google.common.hash.Hashing;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class MainActivity extends AppCompatActivity {
//    String e_key="email";
//    String cp;

    float previousvalueimg = 0.32f;
    float previousvaluefrag = 0.54f;
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
        LottieAnimationView imv = (LottieAnimationView) findViewById(R.id.welcomeView);
        ViewGroup.LayoutParams lp = (ConstraintLayout.LayoutParams)imv.getLayoutParams();

        ValueAnimator va = ValueAnimator.ofFloat(previousvalueimg,f);
        va.setDuration(500);
        va.setInterpolator(new AccelerateDecelerateInterpolator());

        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                System.out.println((float) valueAnimator.getAnimatedValue());
                ((ConstraintLayout.LayoutParams) lp).matchConstraintPercentHeight = (float) valueAnimator.getAnimatedValue();
                imv.setLayoutParams(lp);
            }
        });
        va.start();
        previousvalueimg = f;
    }

    public void makeimgempty()
    {
        LottieAnimationView imv = (LottieAnimationView) findViewById(R.id.welcomeView);
        imv.setImageResource(0);
    }
    public void makefragmentbig(float f)
    {
        ConstraintSet set  = new ConstraintSet();
        FragmentContainerView imv = (FragmentContainerView) findViewById(R.id.RegistrationFrame);
        ViewGroup.LayoutParams lp = (ConstraintLayout.LayoutParams)imv.getLayoutParams();

        ValueAnimator va = ValueAnimator.ofFloat(previousvaluefrag,f);
        va.setDuration(500);
        va.setInterpolator(new AccelerateDecelerateInterpolator());

        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                System.out.println((float) valueAnimator.getAnimatedValue());
                ((ConstraintLayout.LayoutParams) lp).matchConstraintPercentHeight = (float) valueAnimator.getAnimatedValue();
                imv.setLayoutParams(lp);
            }
        });
        va.start();
        previousvaluefrag = f;
        //((ConstraintLayout.LayoutParams) lp).matchConstraintPercentHeight = f;
    }

    public void reloadimg(int img)
    {

        LottieAnimationView imv = (LottieAnimationView) findViewById(R.id.welcomeView);
        imv.pauseAnimation();
        imv.setAnimation(img);
        imv.playAnimation();
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

    public void animationonoff(boolean val)
    {
        if (val)
        {
            LottieAnimationView lottieAnimationView = findViewById(R.id.welcomeView);
            lottieAnimationView.setVisibility(View.VISIBLE);
            lottieAnimationView.playAnimation();
        }
        else{
            LottieAnimationView lottieAnimationView = findViewById(R.id.welcomeView);
            lottieAnimationView.setVisibility(View.INVISIBLE);
            lottieAnimationView.pauseAnimation();
        }
    }

    public void successviewenabler()
    {
        LottieAnimationView successview = (LottieAnimationView) findViewById(R.id.successView);
        successview.setVisibility(View.VISIBLE);
        successview.playAnimation();
    }
}