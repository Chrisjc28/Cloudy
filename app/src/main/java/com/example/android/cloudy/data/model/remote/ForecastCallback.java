package com.example.android.cloudy.data.model.remote;

import com.example.android.cloudy.data.model.remote.pojos.DailyForecast;

import java.util.ArrayList;

/**
 * Created by ccu17 on 25/04/2017.
 */

public interface ForecastCallback {

    void success(ArrayList<DailyForecast> dailyForecasts);

    void failure(String failed);

}
