package com.example.android.cloudy.data.model.remote;

import java.util.List;

/**
 * Created by ccu17 on 25/04/2017.
 */

public interface ForecastCallback {

    void success(List<DailyForecast> dailyForecasts);

    void failure(String failed);

}
