package com.denis.pavlovich.weatherapp.services;

import android.content.Intent;
import android.os.Bundle;

import com.denis.pavlovich.weatherapp.data.provider.IWDataProvider;
import com.denis.pavlovich.weatherapp.data.provider.WeatherDataProviderImpl;
import com.denis.pavlovich.weatherapp.entities.City;
import com.denis.pavlovich.weatherapp.entities.WeatherInfo;
import com.denis.pavlovich.weatherapp.utils.WConstants;

import java.util.List;

public class CityWeatherDetailsService extends AbstractWeatherDetailsService {

    public static final String CITY_WEATHER_DETAILS_SERVICE = "CityWeatherDetailsService";

    private City city;

    @Override
    public void prepareParams(Intent intent) {
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                city = bundle.getParcelable(WConstants.CITY_SELECTED);
            }
        }
    }

    @Override
    public WeatherInfo getWeatherData() {
        IWDataProvider dataProvider = new WeatherDataProviderImpl(city);
        List<WeatherInfo> weatherIfs = dataProvider.getWeatherData();
        if (weatherIfs != null && weatherIfs.size() > 0) {
            return weatherIfs.get(0);
        }
        return null;
    }

    public CityWeatherDetailsService() {
        super(CITY_WEATHER_DETAILS_SERVICE);
    }

}
