package com.example.android.cloudy.data.model.remote;

import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.ANRequest;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.gson.Gson;

import org.json.JSONArray;
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
        try {
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

    @Override
    public void collectForecast(String citySearch, final ForecastCallback cb) {
        if (API_KEY.equals("")) {
            Log.i("CHRIS", "CollectWeatherData: Error with the api key ");
        }

        ANRequest request = AndroidNetworking.get(BASE_URL)
                .addPathParameter("uri", "forecast/daily")
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


    public void jsonParsing(JSONObject response, final ForecastCallback cb) {

        Gson gson = new Gson();

        String GsonString = response.toString();

//        Forecast forecastList = gson.fromJson(GsonString, Forecast.class);
//
//        ArrayList<ForecastListItem> forecastListItems = forecastList.getList();
//
//        cb.success(getForecastInDays(forecastListItems));

    }

//    private TreeMap<String, ArrayList<ForecastListItem>> getForecastInDays(ArrayList<ForecastListItem> originalForecastList) {
//        TreeMap<String, ArrayList<ForecastListItem>> dateFilteredForecast = new TreeMap<>();
//        //this is to pull the day out of the date
//        SimpleDateFormat sdf = new SimpleDateFormat("dd MM yyyy");
//        String day = sdf.format(originalForecastList.get(0).getDate());
//
//        ArrayList<ForecastListItem> forecastForDay = new ArrayList<>();
//
//        for (ForecastListItem item : originalForecastList) {
//            if (sdf.format(item.getDate()).equals(day)) { //in the same day but later forecast
//                forecastForDay.add(item);
//            } else {
//                dateFilteredForecast.put(day, forecastForDay);
//                day = sdf.format(item.getDate());
//                forecastForDay = new ArrayList<>();
//                forecastForDay.add(item);
//            }
//        }
//        return dateFilteredForecast;
//    }
}
