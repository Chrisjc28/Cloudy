
package com.example.android.cloudy.pojos;

import com.google.gson.annotations.Expose;

public class ForecastListItem {

    @Expose
    private Main main;
    @Expose
    private java.util.List<Weather> weather = null;
    @Expose
    private Wind wind;

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public java.util.List<Weather> getWeather() {
        return weather;
    }

    public void setWeather(java.util.List<Weather> weather) {
        this.weather = weather;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

}
