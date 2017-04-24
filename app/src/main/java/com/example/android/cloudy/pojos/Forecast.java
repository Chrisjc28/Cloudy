package com.example.android.cloudy.pojos;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;

/**
 * Created by ccu17 on 24/04/2017.
 */

public class Forecast {

    @Expose
    private ArrayList<ForecastListItem> list;

    public ArrayList<ForecastListItem> getList() {
        return list;
    }

    public void setList(ArrayList<ForecastListItem> list) {
        this.list = list;
    }

}
