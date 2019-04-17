package com.denis.pavlovich.weatherapp.data.provider.rest.entities;

import com.google.gson.annotations.SerializedName;

public class MainModel {

    @SerializedName("temp")        private float temp;
    @SerializedName("pressure")    private int pressure;
    @SerializedName("humidity")    private int humidity;
    @SerializedName("temp_min")    private float tempMin;
    @SerializedName("temp_max")    private float tempMax;

    public float getTemp() {
        return temp;
    }

    public int getPressure() {
        return pressure;
    }

    public int getHumidity() {
        return humidity;
    }

    public float getTempMin() {
        return tempMin;
    }

    public float getTempMax() {
        return tempMax;
    }
}
