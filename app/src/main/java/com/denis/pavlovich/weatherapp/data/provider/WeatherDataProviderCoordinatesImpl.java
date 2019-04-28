package com.denis.pavlovich.weatherapp.data.provider;

import com.denis.pavlovich.weatherapp.data.provider.rest.entities.WeatherResponseModel;

public class WeatherDataProviderCoordinatesImpl extends AbstractGetWeatherData {

    private String lat;

    private String lon;

    public WeatherDataProviderCoordinatesImpl(String lat, String lon) {
        this.lat = lat;
        this.lon = lon;
    }

    @Override
    protected WeatherResponseModel getDataFromSource() {
        return WeatherDataLoader.getWeatherDataByCoordinates(lat, lon);
    }
}
