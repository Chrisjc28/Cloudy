package com.example.android.cloudy.data.model.remote.pojos;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ccu17 on 03/05/2017.
 */

public class Main {

    @SerializedName("temp")
    private Double temp;

    @SerializedName("pressure")
    private Integer pressure;

    @SerializedName("humidity")
    private Integer humidity;

    @SerializedName("temp_min")
    private Integer tempMin;

    @SerializedName("temp_max")
    private Integer tempMax;

    public Double getTemp() {
        return temp;
    }

    public void setTemp(Double temp) {
        this.temp = temp;
    }

    public Integer getPressure() {
        return pressure;
    }

    public void setPressure(Integer pressure) {
        this.pressure = pressure;
    }

    public Integer getHumidity() {
        return humidity;
    }

    public void setHumidity(Integer humidity) {
        this.humidity = humidity;
    }

    public Integer getTempMin() {
        return tempMin;
    }

    public void setTempMin(Integer tempMin) {
        this.tempMin = tempMin;
    }

    public Integer getTempMax() {
        return tempMax;
    }

    public void setTempMax(Integer tempMax) {
        this.tempMax = tempMax;
    }
}
