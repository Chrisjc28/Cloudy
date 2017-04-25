package com.example.android.cloudy.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.cloudy.R;
import com.example.android.cloudy.adpaters.ForecastAdapter;
import com.example.android.cloudy.data.model.remote.CollectWeatherData;
import com.example.android.cloudy.data.model.remote.DailyForecast;
import com.example.android.cloudy.data.model.remote.ForecastCallback;
import com.example.android.cloudy.data.model.remote.ForecastHolder;
import com.example.android.cloudy.data.model.remote.WeatherCallback;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.R.color.black;

public class InitialScreenActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    @BindView(R.id.my_toolbar)
    Toolbar MyToolbar;
    @BindView(R.id.time)
    TextView TimeText;
    @BindView(R.id.day)
    TextView CurrentDay;
    @BindView(R.id.forecast)
    TextView weatherForecast;
    @BindView(R.id.location)
    TextView chosenLocation;
    @BindView(R.id.current_forecast_card_view)
    CardView forecastCardView;
    @BindView(R.id.current_weather_icon)
    ImageView currentWeatherIcon;

    private CollectWeatherData collectWeatherData = new CollectWeatherData();
    public PlaceAutocompleteFragment autocompleteFragment;
    public Menu menuOptions;
    public GoogleApiClient googleApiClient;
    public String selectedPlace;
    private RecyclerView weatherRecyclerView;
    private RecyclerView.Adapter forecastAdapter;
    private RecyclerView.LayoutManager layoutManager;
    public ArrayList<ForecastHolder> fiveDayForecast = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial_screen);
        weatherRecyclerView = (RecyclerView) findViewById(R.id.weather_recycler_view);
        ButterKnife.bind(this);

        MyToolbar.setTitleTextColor(getColor(R.color.menuItems));
        weatherRecyclerView.setHasFixedSize(true);
        weatherRecyclerView.setAdapter(forecastAdapter);

        layoutManager = new LinearLayoutManager(this);
        weatherRecyclerView.setLayoutManager(layoutManager);

        setSupportActionBar(MyToolbar);
        setCurrentTime();
        setCurrentDay();
        googleApiInit();
        collectCurrentWeatherData();
        collectFiveDayForecast();

        autocompleteFragment = (PlaceAutocompleteFragment) getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                selectedPlace = place.getName().toString();
                chosenLocation.setText(place.getName().toString());
                collectCurrentWeatherData();
                collectFiveDayForecast();
            }

            @Override
            public void onError(Status status) {
                Log.i("CHRIS", "An error occurred: " + status);
            }
        });
    }

    @Override
    protected void onStart() {
        googleApiClient.connect();
        super.onStart();
    }

    @Override
    protected void onStop() {
        googleApiClient.disconnect();
        super.onStop();
    }

    @SuppressLint("NewApi")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menuOptions = menu;
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        menu.findItem(R.id.action_share).getIcon().setTint(getColor(R.color.menuItems));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_share:
                //share weather forecast
                break;
            case R.id.action_settings:
                //bring up settings activity, eventually
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.i("CHRIS", "onConnectionFailed: connection failed");
    }

    public void googleApiInit() {
        googleApiClient = new GoogleApiClient
                .Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(this, this)
                .build();
    }

    public void setCurrentTime() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");
        String currentDateTimeString = df.format(c.getTime());
        TimeText.setText(currentDateTimeString);
    }


    public void setCurrentDay() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("EEEE");
        String currentDateTimeString = df.format(c.getTime());
        CurrentDay.setText(currentDateTimeString);
    }


    public void collectCurrentWeatherData() {
        collectWeatherData.collectWeather(selectedPlace , new WeatherCallback() {
            @Override
            public void success(String description, double tempMin, double tempMax, double windInMph) {
                int minTempInCelcious = (int) (tempMin - 273.15);
                int maxTempInCelcious = (int) (tempMax - 273.15);

                //todo: Add more option to switch statement

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

                weatherForecast.setTextColor(getResources().getColor(black));
                weatherForecast.setText("The Weather forecast for today is " + description + "."
                        + "\nThe Minimum temp is " + minTempInCelcious + "°C"
                        + " & Maximum temp is " + maxTempInCelcious + "°C."
                + "\nWind speed is " + windInMph + "mph");

            }

            @Override
            public void failure(String failed) {
                Log.i("CHRIS", "Sorry there was an error displaying the weather");
            }
        });
    }

    public void collectFiveDayForecast() {
        collectWeatherData.collectForecast(selectedPlace, new ForecastCallback() {
            @Override
            public void success(ArrayList<DailyForecast> dailyForecasts) {
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

            int minTempInCelcious = (int) (tempMin - 273.15);
            int maxTempInCelcious = (int) (tempMax - 273.15);

            fiveDayForecast.add(new ForecastHolder(date, description, windForecast, minTempInCelcious, maxTempInCelcious));
        }
        return fiveDayForecast;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }
}
