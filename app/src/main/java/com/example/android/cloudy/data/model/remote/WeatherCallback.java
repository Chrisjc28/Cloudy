package com.example.android.cloudy.data.model.remote;

import com.example.android.cloudy.data.model.remote.pojos.WeatherResponse;

/**
 * Created by ccu17 on 21/04/2017.
 */

public interface WeatherCallback {

    void success(WeatherResponse weatherResponse);

    void failure(String failed);
}
