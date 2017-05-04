package com.example.android.cloudy.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.cloudy.R;
import com.google.android.gms.location.places.Place;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ccu17 on 26/04/2017.
 */

public class FavouriteFragment extends Fragment implements PlaceSelected{

//    public String selectedPlace;

    ArrayList<String> chosenFavourites;

    @BindView(R.id.fav)
    TextView favourites;

    public FavouriteFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favourite_forecasts, container, false);
        ButterKnife.bind(this, view);

//        this.selectedPlace = ((InitialScreenActivity)getActivity()).selectedPlace;
//        favourites.setText(selectedPlace);
        return view;
    }

    @Override
    public void placeSelected(Place place) {
//        chosenFavourites.add(place.getAddress().toString());
//        favourites.setText(chosenFavourites.indexOf(0));
    }

}
