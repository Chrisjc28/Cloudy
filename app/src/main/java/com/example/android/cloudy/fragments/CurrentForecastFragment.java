package com.example.android.cloudy.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.cloudy.R;
import com.example.android.cloudy.activity.InitialScreenActivity;
import com.example.android.cloudy.data.model.remote.CollectWeatherData;
import com.example.android.cloudy.data.model.remote.WeatherCallback;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.location.places.ui.SupportPlaceAutocompleteFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ccu17 on 26/04/2017.
 */

public class CurrentForecastFragment extends Fragment implements GoogleApiClient.OnConnectionFailedListener {

    @BindView(R.id.day)
    TextView CurrentDay;
    @BindView(R.id.description)
    TextView weatherDescription;
    @BindView(R.id.minTemp)
    TextView currentMinTemp;
    @BindView(R.id.maxTemp)
    TextView currentMaxTemp;
    @BindView(R.id.windSpeed)
    TextView windSpeed;
    @BindView(R.id.location)
    TextView chosenLocation;
    @BindView(R.id.current_forecast_card_view)
    CardView forecastCardView;
    @BindView(R.id.current_weather_icon)
    ImageView currentWeatherIcon;
    @BindView(R.id.favourites)
    Button addFavourites;

    private CollectWeatherData collectWeatherData = new CollectWeatherData();
    public SupportPlaceAutocompleteFragment autocompleteFragment;
    public Menu menuOptions;
    public GoogleApiClient googleApiClient;
    private static View view;

    public String selectedPlace;

    public CurrentForecastFragment() {
        // Required empty public constructor
    }

    public static CurrentForecastFragment newInstance() {
        CurrentForecastFragment fragment = new CurrentForecastFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_current_forecast, container, false);
        ButterKnife.bind(this, view);

        setCurrentDay();

        googleApiInit();

        addFavourites.setText(R.string.add_fav);

        addFavourites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "I was pressed" ,Toast.LENGTH_LONG).show();
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (autocompleteFragment == null) {
            autocompleteFragment = new SupportPlaceAutocompleteFragment();

            autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {

                @Override
                public void onPlaceSelected(Place place) {
                    selectedPlace = place.getName().toString();
                    chosenLocation.setText(place.getName().toString());
                    collectCurrentWeatherData();

                    ((InitialScreenActivity)getActivity()).placeSelected(place);
                }

                @Override
                public void onError(Status status) {
                    Log.i("CHRIS", "An error occurred: " + status);
                }
            });

            FragmentManager fm = getChildFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();

            ft.add(R.id.place_autocomplete_fragment, autocompleteFragment);
            ft.commit();
            fm.executePendingTransactions();
        }

    }

    @Override
    public void onStart() {
        googleApiClient.connect();
        super.onStart();
    }


    @Override
    public void onStop() {
        googleApiClient.disconnect();
        super.onStop();
    }

    public void googleApiInit() {
        googleApiClient = new GoogleApiClient
                .Builder(this.getActivity())
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage((FragmentActivity) this.getActivity(), this)
                .build();
    }


    public void setCurrentDay() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("EEE, d MMM yyyy");
        String currentDateTimeString = df.format(c.getTime());
        CurrentDay.setText(currentDateTimeString);
    }

    public void collectCurrentWeatherData() {
        collectWeatherData.collectWeather(selectedPlace , new WeatherCallback() {
            @Override
            public void success(String description, double tempMin, double tempMax, double windInMph) {

                switch (description) {
                    case "clear sky":
                        currentWeatherIcon.setImageResource(R.drawable.sunny);
                        break;
                    case "mist":
                        currentWeatherIcon.setImageResource(R.drawable.mist);
                        break;
                    case "few clouds":
                        currentWeatherIcon.setImageResource(R.drawable.cloudy);
                        break;
                    case "scattered clouds":
                        currentWeatherIcon.setImageResource(R.drawable.cloudy);
                        break;
                    case "overcast clouds":
                        currentWeatherIcon.setImageResource(R.drawable.cloudy);
                        break;
                    case "heavy intensity rain":
                        currentWeatherIcon.setImageResource(R.drawable.rain);
                        break;
                    case "light intensity shower rain":
                        currentWeatherIcon.setImageResource(R.drawable.rain);
                        break;
                    case "shower rain":
                        currentWeatherIcon.setImageResource(R.drawable.rain);
                        break;
                    case "light rain":
                        currentWeatherIcon.setImageResource(R.drawable.rain);
                        break;
                    default:
                        currentWeatherIcon.setImageResource(R.drawable.sunny);
                        break;
                }

                weatherDescription.setText(String.format("The Weather forecast for today is %s", description));
                currentMinTemp.setText(String.format("The minimum temp is %s", tempMin + "°C"));
                currentMaxTemp.setText(String.format("The maximum temp is %s", tempMax + "°C"));
                windSpeed.setText(String.format("The current wind speed is %s", windInMph + " KPH"));


            }

            @Override
            public void failure(String failed) {
                Log.i("CHRIS", "Sorry there was an error displaying the weather");
            }
        });
    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
