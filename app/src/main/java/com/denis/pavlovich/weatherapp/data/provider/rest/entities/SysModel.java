package com.denis.pavlovich.weatherapp.data.provider.rest.entities;

import com.google.gson.annotations.SerializedName;

class SysModel {

    @SerializedName("type")     private int type;
    @SerializedName("id")       private int id;
    @SerializedName("message")  private float message;
    @SerializedName("country")  private String country;
    @SerializedName("sunrise")  private long sunrise;
    @SerializedName("sunset")   private long sunset;

    public int getType() {
        return type;
    }

    public int getId() {
        return id;
    }

    public float getMessage() {
        return message;
    }

    public String getCountry() {
        return country;
    }

    public long getSunrise() {
        return sunrise;
    }

    public long getSunset() {
        return sunset;
    }
}
