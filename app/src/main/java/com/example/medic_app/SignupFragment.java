package com.example.medic_app;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class SignupFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.signup_fragment_layout, container, false);
//        ImageView imw=(ImageView)view.findViewById(R.id.imageView);
//        int hp=imw.getResources().getDisplayMetrics().heightPixels;
//        imw.getLayoutParams().height=(int)(0.5*hp);
        return view;
    }
}