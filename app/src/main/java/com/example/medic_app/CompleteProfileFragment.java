package com.example.medic_app;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CompleteProfileFragment extends Fragment {

    String[] bloodgroups = { "A+", "A-",
            "B+", "B-",
            "O+", "O-","AB-","AB+" };
    String[] gender = {"Male","Female","Prefer not to Say"};
    List<String> genderlist = Arrays.asList(gender);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_complete_profile, container, false);
        Spinner genderspinner = (Spinner) view.findViewById(R.id.GenderSpinner);
        return view;
    }
}