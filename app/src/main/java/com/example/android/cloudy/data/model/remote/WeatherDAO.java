package com.example.android.cloudy.data.model.remote;

/**
 * Created by ccu17 on 21/04/2017.
 */

public interface WeatherDAO {

    void collectWeather(String citySearch, WeatherCallback cb);
}
