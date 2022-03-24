package com.example.silver_screen;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class Fragment_bottom extends Fragment {



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_bottom, container, false);
        Button home = view.findViewById(R.id.fragment_home);
        Button profile = view.findViewById(R.id.fragment_profile);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                go_to_home();
            }
        });


        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                go_to_profile();
            }
        });
        return view;
    }

    public void go_to_profile() {
        Intent f_profile = new Intent(getActivity(), profile_page.class);
        startActivity(f_profile);
    }

    public void go_to_home() {
        Intent f_home = new Intent(getActivity(), Home_page.class);
        startActivity(f_home);
    }
}


