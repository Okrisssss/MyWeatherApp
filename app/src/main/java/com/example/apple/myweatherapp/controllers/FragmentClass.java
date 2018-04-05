package com.example.apple.myweatherapp.controllers;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.apple.weatherapplication.R;


public class FragmentClass extends Fragment {

    private TextView weatherTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.weather_fragment, container ,false);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        weatherTextView = (TextView)view.findViewById(R.id.weatherTextView);
        String city = getArguments().getString("city");
        weatherTextView.setText(city);
    }
}
