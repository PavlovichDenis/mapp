package com.denis.pavlovich.weatherapp.services;

import android.content.Intent;
import android.os.Bundle;

import com.denis.pavlovich.weatherapp.data.provider.IWDataProvider;
import com.denis.pavlovich.weatherapp.data.provider.WeatherDataProviderCoordinatesImpl;
import com.denis.pavlovich.weatherapp.entities.Coordinates;
import com.denis.pavlovich.weatherapp.entities.WeatherInfo;
import com.denis.pavlovich.weatherapp.utils.WConstants;

import java.util.List;

public class CoordinatesWeatherDetailsService extends AbstractWeatherDetailsService {

    public static final String COORDINATES_WEATHER_DETAILS_SERVICE = "CoordinatesWeatherDetailsService";

    private Coordinates coordinates;

    @Override
    void prepareParams(Intent intent) {
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                coordinates = bundle.getParcelable(WConstants.COORDINATES_SELECTED);
                if (coordinates == null) {
                    coordinates = new Coordinates("0", "0");
                }
            }
        }
    }

    @Override
    WeatherInfo getWeatherData() {
        IWDataProvider dataProvider = new WeatherDataProviderCoordinatesImpl(coordinates.getLat(), coordinates.getLon());
        List<WeatherInfo> weatherIfs = dataProvider.getWeatherData();
        if (weatherIfs != null && weatherIfs.size() > 0) {
            return weatherIfs.get(0);
        }
        return null;
    }

    public CoordinatesWeatherDetailsService() {
        super(COORDINATES_WEATHER_DETAILS_SERVICE);
    }
}
