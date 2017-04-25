package com.example.android.cloudy.data.model.remote;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ccu17 on 25/04/2017.
 */

public class ListOfDailyForecasts {

    @SerializedName("list")
    @Expose
    private List<DailyForecast> list = null;

    public List<DailyForecast> getList() {
        return list;
    }

}
