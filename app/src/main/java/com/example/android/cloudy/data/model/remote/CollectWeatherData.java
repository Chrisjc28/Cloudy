package com.example.android.cloudy.data.model.remote;

import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.ANRequest;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.android.cloudy.data.model.remote.pojos.ListOfDailyForecasts;
import com.example.android.cloudy.data.model.remote.pojos.WeatherResponse;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ccu17 on 21/04/2017.
 */

public class CollectWeatherData implements WeatherDAO {

    private static final String BASE_URL = "http://api.openweathermap.org/data/2.5/{uri}";
    private static final String API_KEY = "91b9b78861116f95facbc935907c6ae5";

    @Override
    public void collectWeather(String citySearch, final WeatherCallback cb) {
        if (API_KEY == null) {
            Log.i("CHRIS", "CollectWeatherData: Error with the api key ");
        }

        ANRequest request = AndroidNetworking.get(BASE_URL)
                .addPathParameter("uri", "weather")
                .addQueryParameter("q", citySearch)
                .addQueryParameter("units", "metric")
                .addQueryParameter("appid", API_KEY)
                .setPriority(Priority.LOW)
                .build();

        request.getAsJSONObject(new JSONObjectRequestListener() {
            @Override
            public void onResponse(JSONObject response) {
                jsonParsing(response, cb);
            }

            @Override
            public void onError(ANError anError) {

            }
        });

    }


    public void jsonParsing(JSONObject response ,final WeatherCallback cb) {
        WeatherResponse weatherResponse = new Gson().fromJson(response.toString(), WeatherResponse.class);

        String weather = weatherResponse.weather[0].getDescription();
        double tempMin = weatherResponse.main.getTempMin();
        double tempMax = weatherResponse.main.getTempMax();
        double wind =  weatherResponse.wind.getSpeed();

        cb.success(weather, tempMin, tempMax, wind);
    }

    @Override
    public void collectForecast(String citySearch, final ForecastCallback cb) {
        if (API_KEY.equals("")) {
            Log.i("CHRIS", "CollectWeatherData: Error with the api key ");
        }

        ANRequest request = AndroidNetworking.get(BASE_URL)
                .addPathParameter("uri", "forecast/daily")
                .addQueryParameter("q", citySearch)
                .addQueryParameter("units", "metric")
                .addQueryParameter("appid", API_KEY)
                .setPriority(Priority.LOW)
                .build();

        request.getAsJSONObject(new JSONObjectRequestListener() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    jsonParsing(response, cb);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onError(ANError anError) {

            }
        });

    }

    public void jsonParsing(JSONObject response, final ForecastCallback cb) throws JSONException {
        ListOfDailyForecasts dailyForecast = new Gson().fromJson(response.toString(), ListOfDailyForecasts.class);
        cb.success(dailyForecast.getList());

    }

}
