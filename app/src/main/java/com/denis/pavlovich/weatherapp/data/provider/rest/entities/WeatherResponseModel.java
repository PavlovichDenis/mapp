package com.denis.pavlovich.weatherapp.data.provider.rest.entities;

import com.google.gson.annotations.SerializedName;

public class WeatherResponseModel {

    @SerializedName("coord")      private CoordModel coordinates;
    @SerializedName("weather")    private WeatherModel[] weather;
    @SerializedName("base")       private String base;
    @SerializedName("main")       private MainModel main;
    @SerializedName("visibility") private int visibility;
    @SerializedName("wind")       private WindModel wind;
    @SerializedName("clouds")     private CloudsModel clouds;
    @SerializedName("dt")         private long dt;
    @SerializedName("sys")        private SysModel sys;
    @SerializedName("id")         private long id;
    @SerializedName("name")       private String name;
    @SerializedName("cod")        private int cod;

    public WeatherModel[] getWeather() {
        return weather;
    }

    public MainModel getMain() {
        return main;
    }

    public WindModel getWind() {
        return wind;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public CoordModel getCoordinates() {
        return coordinates;
    }

    public String getBase() {
        return base;
    }

    public int getVisibility() {
        return visibility;
    }

    public CloudsModel getClouds() {
        return clouds;
    }

    public long getDt() {
        return dt;
    }

    public SysModel getSys() {
        return sys;
    }

    public int getCod() {
        return cod;
    }
}
