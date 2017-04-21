package com.example.android.cloudy.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.cloudy.R;
import com.example.android.cloudy.data.model.remote.CollectCurrentWeather;
import com.example.android.cloudy.data.model.remote.WeatherCallback;
import com.example.android.cloudy.fragments.WeatherFragment;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InitialScreenActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    WeatherFragment weatherFragment = new WeatherFragment();

    @BindView(R.id.my_toolbar)
    Toolbar MyToolbar;
    @BindView(R.id.time)
    TextView TimeText;
    @BindView(R.id.day)
    TextView CurrentDay;
    @BindView(R.id.refresh_icon)
    ImageView refreshIcon;
    @BindView(R.id.updated_time)
    TextView lastUpdated;
    @BindView(R.id.forecast)
    TextView weatherForecast;

    private CollectCurrentWeather collectCurrentWeather = new CollectCurrentWeather();

    public PlaceAutocompleteFragment autocompleteFragment;
    public Menu menuOptions;
    public GoogleApiClient mGoogleApiClient;
    public String selectedPlace;


    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial_screen);
        ButterKnife.bind(this);

        MyToolbar.setTitleTextColor(getColor(R.color.menuItems));

        setSupportActionBar(MyToolbar);
        setCurrentTime();
        setCurrentDay();
        setLastUpdated();
        refreshData();

        autocompleteFragment = (PlaceAutocompleteFragment) getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                Log.i("CHRIS", "Place: " + place.getName());
                selectedPlace = place.getName().toString();
                collectCurrentWeather.collectCurrentWeather(selectedPlace , new WeatherCallback() {
                    @Override
                    public void success(String description, double temp) {
                        int tempInCelcious = (int) (temp - 273.15);
                        weatherForecast.setText(description + " " + tempInCelcious);
                    }

                    @Override
                    public void failure(String failed) {
                        Log.i("CHRIS", "Sorry there was an error displaying the weather");
                    }
                });
            }

            @Override
            public void onError(Status status) {
                Log.i("CHRIS", "An error occurred: " + status);
            }
        });



        mGoogleApiClient = new GoogleApiClient
                .Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(this, this)
                .build();

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

    public void setCurrentTime() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");
        String currentDateTimeString = df.format(c.getTime());
        TimeText.setText(currentDateTimeString);
    }

    public void setLastUpdated() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");
        String lastUpDated = df.format(c.getTime());
        lastUpdated.setText("Last updated " + lastUpDated);
    }

    public void setCurrentDay() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("EEEE");
        String currentDateTimeString = df.format(c.getTime());
        CurrentDay.setText(currentDateTimeString);
    }

    public void refreshData() {
        refreshIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLastUpdated();
            }
        });
    }
}