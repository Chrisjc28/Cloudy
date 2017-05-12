package com.example.android.cloudy.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.example.android.cloudy.R;
import com.example.android.cloudy.activity.InitialScreenActivity;
import com.example.android.cloudy.data.model.remote.CollectWeatherData;
import com.example.android.cloudy.data.model.remote.WeatherCallback;
import com.example.android.cloudy.data.model.remote.pojos.WeatherResponse;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.location.places.ui.SupportPlaceAutocompleteFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ccu17 on 26/04/2017.
 */

public class CurrentForecastFragment extends Fragment implements GoogleApiClient.OnConnectionFailedListener {

    private static final int MAX_FAVOURITES = 3;

    @BindView(R.id.parent_holder)
    LinearLayout parent;
    @BindView(R.id.day)
    TextView CurrentDay;
    @BindView(R.id.description)
    TextView weatherDescription;
    @BindView(R.id.min_temp)
    TextView currentMinTemp;
    @BindView(R.id.max_temp)
    TextView currentMaxTemp;
    @BindView(R.id.wind_speed)
    TextView windSpeed;
    @BindView(R.id.wind_direction)
    ImageView currentWindDirection;
    @BindView(R.id.location)
    TextView chosenLocation;
    @BindView(R.id.current_forecast_card_view)
    CardView forecastCardView;
    @BindView(R.id.current_weather_icon)
    ImageView currentWeatherIcon;
    @BindView(R.id.favourites)
    Button addFavourites;
    @BindView(R.id.flipper)
    ViewFlipper viewFlipper;
    @BindView(R.id.no_weather_image)
    ImageView noWeatherDataImage;

    private CollectWeatherData collectWeatherData = new CollectWeatherData();
    public SupportPlaceAutocompleteFragment autocompleteFragment;
    public Menu menuOptions;
    public GoogleApiClient googleApiClient;
    public String description;


    public String selectedPlace = null;

    public CurrentForecastFragment() {
        // Required empty public constructor
    }

    public static CurrentForecastFragment newInstance() {
        CurrentForecastFragment fragment = new CurrentForecastFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_current_forecast, container, false);
        ButterKnife.bind(this, view);
        setCurrentDay();
        googleApiInit();

        parent.getChildAt(1).setVisibility(View.GONE);
        parent.getChildAt(2).setVisibility(View.GONE);
        parent.getChildAt(3).setVisibility(View.GONE);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        isInitialState();

        if (autocompleteFragment == null) {
            autocompleteFragment = new SupportPlaceAutocompleteFragment();

            autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {

                @Override
                public void onPlaceSelected(Place place) {
                    selectedPlace = place.getAddress().toString();
                    chosenLocation.setText(place.getName().toString());
                    collectCurrentWeatherData();
                    ((InitialScreenActivity) getActivity()).placeSelected(place);
                    isInitialState();
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

        addFavourites.setText(R.string.add_fav);

        addFavourites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveToFavourites(selectedPlace);
                refreshFavourites();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        googleApiClient.connect();
    }


    @Override
    public void onStop() {
        super.onStop();
        googleApiClient.disconnect();

    }

    @Override
    public void onResume() {
        super.onResume();
        refreshFavourites();
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
        collectWeatherData.collectWeather(selectedPlace, new WeatherCallback() {
            @Override
            public void success(WeatherResponse weatherResponse) {

                // top item only
                String weather = weatherResponse.weather[0].getDescription();
                double tempMin = weatherResponse.main.getTempMin();
                double tempMax = weatherResponse.main.getTempMax();
                double wind =  weatherResponse.wind.getSpeed();
                double windDirection = weatherResponse.wind.hasDeg() ? weatherResponse.wind.getDeg() : 0;


                weatherDescription.setText((weather));
                currentWeatherIcon.setImageResource(getWeatherIconFromDescription(weather));
                currentMinTemp.setText(String.format(tempMin + "°C min"));
                currentMaxTemp.setText(String.format(tempMax + "°C max"));
                windSpeed.setText(String.format("The wind speed is %s", wind + " KPH"));
                currentWindDirection.setImageResource(R.drawable.ic_arrow_upward);

                // favourite refresh
                refreshFavourites();

            }

            @Override
            public void failure(String failed) {
                Log.i("CHRIS", "Sorry there was an error displaying the weather");
            }
        });
    }

    //todo: convert to enum
    private int getWeatherIconFromDescription(String description) {
        switch (description) {
            case "clear sky": return R.drawable.sunny;
            case "mist": return R.drawable.mist;
            case "few clouds": return R.drawable.cloudy;
            case "scattered clouds": return R.drawable.cloudy;
            case "broken clouds": return R.drawable.cloudy;
            case "overcast clouds": return R.drawable.cloudy;
            case "heavy intensity rain": return R.drawable.rain;
            case "light intensity shower rain": return R.drawable.rain;
            case "shower rain": return R.drawable.rain;
            case "light rain": return R.drawable.rain;
            case "haze": return R.drawable.mist;
            default: return R.drawable.sunny;
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    //todo: move things to constants (e.g., favourites string)
    private void saveToFavourites(String favourite) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        SharedPreferences.Editor editor = pref.edit();

        Set<String> favList = pref.getStringSet("favourites", new HashSet<String>());

        Set<String> set = new HashSet<String>();

        if (favList.size() < MAX_FAVOURITES) {
            set.addAll(getFavourite());
            set.add(favourite);
            pref.edit().putStringSet("favourites", set).apply();
        } else {
            Toast.makeText(getActivity(), "I pity the fool who tries to add more than 3 favourites", Toast.LENGTH_LONG).show();
        }
    }

    //todo: rename
    private HashSet<String> getFavourite() {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        HashSet<String> tmp = (HashSet<String>) pref.getStringSet("favourites", new HashSet<String>());
        Log.i("SHANE", "Retreived: " + tmp.size());
        return tmp;

    }

    private void refreshFavourites() {
        int i = 0;
        for (final String favoriteCity : getFavourite()) {
            View childView = parent.getChildAt(i+1);
            childView.setVisibility(View.VISIBLE);

            final TextView dateTextView = (TextView) childView.findViewById(R.id.day);
            final TextView locationTextView = (TextView) childView.findViewById(R.id.location);
            final ImageView weatherIconImageView = (ImageView) childView.findViewById(R.id.current_weather_icon);
            final TextView descriptionTextView = (TextView) childView.findViewById(R.id.description);
            final TextView minTempTextView = (TextView) childView.findViewById(R.id.min_temp);
            final TextView maxTempTextView = (TextView) childView.findViewById(R.id.max_temp);
            final TextView windSpeedTextView = (TextView) childView.findViewById(R.id.wind_speed);
            final ImageView windImageView = (ImageView) childView.findViewById(R.id.wind_direction);
            final Button addFavButton = (Button) childView.findViewById(R.id.favourites);

            collectWeatherData.collectWeather(favoriteCity, new WeatherCallback() {
                @Override
                public void success(WeatherResponse weatherResponse) {

                    String weather = weatherResponse.weather[0].getDescription();
                    double tempMin = weatherResponse.main.getTempMin();
                    double tempMax = weatherResponse.main.getTempMax();
                    double wind =  weatherResponse.wind.getSpeed();
                    double windDirection = weatherResponse.wind.hasDeg() ? weatherResponse.wind.getDeg() : 0;

                    dateTextView.setVisibility(View.GONE);
                    locationTextView.setText(favoriteCity);
                    weatherIconImageView.setImageResource(getWeatherIconFromDescription(weather));
                    descriptionTextView.setText(weather);
                    minTempTextView.setText(String.format(tempMin + "°C min"));
                    maxTempTextView.setText(String.format(tempMax + "°C max"));
                    windSpeedTextView.setText(String.format("The wind speed is %s", wind + " KPH"));
                    windImageView.setImageResource(R.drawable.ic_arrow_upward);
                    addFavButton.setVisibility(View.GONE);

                }

                @Override
                public void failure(String failed) {
                    Log.i("CHRIS", "Sorry there was an error displaying the weather");
                }
            });

            i++;
        }
    }

    public void isInitialState() {
        if (getFavourite().size() <= 0 && (selectedPlace == null || selectedPlace.trim().equals("") )) {
            viewFlipper.setDisplayedChild(1);
            noWeatherDataImage.setColorFilter(getResources().getColor(R.color.menuItems));

        } else {
            viewFlipper.setDisplayedChild(0);
        }
    }
}
