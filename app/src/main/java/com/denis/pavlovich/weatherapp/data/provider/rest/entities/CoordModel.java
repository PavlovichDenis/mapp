package com.denis.pavlovich.weatherapp.data.provider.rest.entities;

import com.google.gson.annotations.SerializedName;

public class CoordModel {

    @SerializedName("lon") private float lon;
    @SerializedName("lat") private float lat;

    public float getLon() {
        return lon;
    }

    public float getLat() {
        return lat;
    }
}
