package com.example.android.cloudy.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.cloudy.R;

import butterknife.ButterKnife;

/**
 * Created by ccu17 on 26/04/2017.
 */

public class GoogleMapsFragment extends Fragment {

    public GoogleMapsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_google_maps, container, false);
        ButterKnife.bind(this, view);
        return view;
    }
}
