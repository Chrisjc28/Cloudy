package com.example.android.cloudy.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.cloudy.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ccu17 on 21/04/2017.
 */

public class WeatherFragment extends Fragment {

    @BindView(R.id.forecast)
    TextView foreCastText;

    public WeatherFragment() {
    }

    public static WeatherFragment newInstance() {
        WeatherFragment fragment = new WeatherFragment();
        return fragment;
    }

    @Override
    public void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState ) {
        View v =  inflater.inflate(R.layout.fragment_weather, container, false);
        ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public void onAttach( Context context ) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}