package com.denis.pavlovich.weatherapp.data.provider.rest.entities;

import com.google.gson.annotations.SerializedName;

public class WeatherModel {

    @SerializedName("id")          private int id;
    @SerializedName("main")        private String main;
    @SerializedName("description") private String description;
    @SerializedName("icon")        private String icon;

    public String getDescription() {
        return description;
    }

    public int getId() {
        return id;
    }

    public String getMain() {
        return main;
    }

    public String getIcon() {
        return icon;
    }
}
