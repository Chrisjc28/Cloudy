package com.example.android.cloudy.data.model.remote.pojos;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ccu17 on 25/04/2017.
 */

public class Temp {

    @SerializedName("day")
    private double day;

    @SerializedName("min")
    private double min;

    @SerializedName("max")
    private double max;

    @SerializedName("night")
    private double night;

    @SerializedName("eve")
    private double eve;
    @SerializedName("morn")
    private double morn;

    public Double getDay() {
        return day;
    }

    public void setDay(Double day) {
        this.day = day;
    }

    public double getMin() {
        return min;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
    }

    public Double getNight() {
        return night;
    }

    public void setNight(Double night) {
        this.night = night;
    }

    public Double getEve() {
        return eve;
    }

    public void setEve(Double eve) {
        this.eve = eve;
    }

    public Double getMorn() {
        return morn;
    }

    public void setMorn(Double morn) {
        this.morn = morn;
    }

}
