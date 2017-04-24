package com.example.android.cloudy.data.model.remote;

import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.ANRequest;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.android.cloudy.pojos.Forecast;
import com.example.android.cloudy.pojos.ForecastListItem;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by ccu17 on 24/04/2017.
 */

public class CollectFiveDayForecast implements WeatherDAO {

    private static final String BASE_URL = "http://api.openweathermap.org/data/2.5/{uri}";
    private static final String API_KEY = "91b9b78861116f95facbc935907c6ae5";

    @Override
    public void collectWeather(String citySearch, final WeatherCallback cb) {
        if (API_KEY.equals("")) {
            Log.i("CHRIS", "CollectCurrentWeather: Error with the api key ");
        }

        ANRequest request = AndroidNetworking.get(BASE_URL)
                .addPathParameter("uri", "forecast")
                .addQueryParameter("q", citySearch)
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

        Gson gson = new Gson();

        String GsonString = response.toString();

        Forecast forecastList = gson.fromJson(GsonString, Forecast.class);

        ArrayList<ForecastListItem> forecastListItems = forecastList.getList();

        // HashMap ourlist = sortForecastIntoDays(forecastListItem)
        // ourlist.get(new Date());


        try {
            JSONArray foreCastList = response.getJSONArray("list");
            for (int i = 0; i < foreCastList.length(); i++) {

            }




            JSONArray weather = response.getJSONArray("weather");
            String description = "";
            for (int i = 0; i < weather.length(); i++) {
                description = weather.getJSONObject(i).getString("description");
            }
            JSONObject main = response.getJSONObject("main");
            double tempMin = Double.parseDouble(main.getString("temp_min"));
            double tempMax = Double.parseDouble(main.getString("temp_max"));
            JSONObject wind = response.getJSONObject("wind");
            double windInMph = Double.parseDouble(wind.getString("speed"));
            cb.success(description, tempMin, tempMax, windInMph);
        } catch (JSONException e) {
            e.printStackTrace();
            cb.failure("Failed");
        }
    }

    private HashMap<Date, ArrayList<ForecastListItem>> sortForecastIntoDays(ArrayList<ForecastListItem> originalForecastList) {

        return null;
    }

}
