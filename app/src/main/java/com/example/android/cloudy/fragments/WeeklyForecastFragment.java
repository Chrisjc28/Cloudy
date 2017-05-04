package com.example.android.cloudy.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.cloudy.R;
import com.example.android.cloudy.adpaters.ForecastAdapter;
import com.example.android.cloudy.data.model.remote.CollectWeatherData;
import com.example.android.cloudy.data.model.remote.ForecastCallback;
import com.example.android.cloudy.data.model.remote.ForecastHolder;
import com.example.android.cloudy.data.model.remote.pojos.DailyForecast;
import com.google.android.gms.location.places.Place;

import java.util.ArrayList;

import butterknife.ButterKnife;

/**
 * Created by ccu17 on 26/04/2017.
 */

public class WeeklyForecastFragment extends Fragment implements PlaceSelected {

    private CollectWeatherData collectWeatherData = new CollectWeatherData();

    public String selectedPlace;
    private RecyclerView weatherRecyclerView;
    private RecyclerView.Adapter forecastAdapter;
    private RecyclerView.LayoutManager layoutManager;
    public ArrayList<ForecastHolder> fiveDayForecast = new ArrayList<>();

    public WeeklyForecastFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_weekly_day_forecast, container, false);
        ButterKnife.bind(this, view);

        weatherRecyclerView = (RecyclerView) view.findViewById(R.id.weather_recycler_view);
        weatherRecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        weatherRecyclerView.setLayoutManager(layoutManager);

        forecastAdapter = new ForecastAdapter(fiveDayForecast);
        weatherRecyclerView.setAdapter(forecastAdapter);

        return view;

    }

    public void CollectFiveDayForecast() {
        collectWeatherData.collectForecast(selectedPlace, new ForecastCallback() {
            @Override
            public void success(ArrayList<DailyForecast> dailyForecasts) {
                Log.i("CHRIS", "success:  successful");
                forecastAdapter = new ForecastAdapter(getWindForecast(dailyForecasts));
                weatherRecyclerView.setAdapter(forecastAdapter);
            }

            @Override
            public void failure(String failed) {
                Log.i("CHRIS", "Sorry there was an error displaying the weather");
            }
        });
    }

    private ArrayList<ForecastHolder> getWindForecast(ArrayList<DailyForecast> dailyForecasts) {
        fiveDayForecast.clear();
        for (int i = 0; i < dailyForecasts.size(); i++) {
            long date = dailyForecasts.get(i).getDt();

            String description = dailyForecasts.get(i).getWeather().get(0).getDescription();

            double windForecast = dailyForecasts.get(i).getSpeed();

            double tempMin = dailyForecasts.get(i).getTemp().getMin();
            double tempMax = dailyForecasts.get(i).getTemp().getMax();

            int minTempInCelcious = (int) (tempMin);
            int maxTempInCelcious = (int) (tempMax);

            fiveDayForecast.add(new ForecastHolder(date, description, maxTempInCelcious, minTempInCelcious, windForecast));
        }
        return fiveDayForecast;
    }

    @Override
    public void placeSelected(Place place) {
        selectedPlace = place.getName().toString();
        CollectFiveDayForecast();
    }
}
