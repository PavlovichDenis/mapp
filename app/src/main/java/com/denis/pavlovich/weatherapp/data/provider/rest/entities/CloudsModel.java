package com.denis.pavlovich.weatherapp.data.provider.rest.entities;

import com.google.gson.annotations.SerializedName;

public class CloudsModel {

    @SerializedName("all") private int all;

    public int getAll() {
        return all;
    }
}
