package com.example.android.cloudy.data.model.remote;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Created by ccu17 on 25/04/2017.
 */

public class ForecastHolder {

    long date;

    double wind;

    double tempMin;

    double tempMax;

    String description;

    public ForecastHolder(long date, String description, double tempMax, double tempMin, double wind) {
        this.date = date;
        this.description = description;
        this.tempMax = tempMax;
        this.tempMin = tempMin;
        this.wind = wind;
    }

    public String getDate() {
        DateFormat format = new SimpleDateFormat("EEEE dd/MM/yyyy");
        String formatted = format.format(date * 1000);
        return String.valueOf(formatted);
    }

    public String getWind() {
        return String.valueOf(wind);
    }

    public String getTempMax() {
        return String.valueOf(tempMax);
    }

    public String getTempMin() {
        return String.valueOf(tempMin);
    }

    public String getDescription() {
        return description;
    }
}
