package com.example.android.cloudy.data.model.remote.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by ccu17 on 25/04/2017.
 */

public class ListOfDailyForecasts {

    @SerializedName("list")
    @Expose
    private ArrayList<DailyForecast> list = null;

    public ArrayList<DailyForecast> getList() {
        return list;
    }

}
