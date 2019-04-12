package com.denis.pavlovich.weatherapp.services;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.denis.pavlovich.weatherapp.data.provider.IWDataProvider;
import com.denis.pavlovich.weatherapp.data.provider.WeatherDataProviderImpl;
import com.denis.pavlovich.weatherapp.entities.WeatherInfo;
import com.denis.pavlovich.weatherapp.utils.WConstants;

import java.util.List;

public class WeatherDetailsService extends IntentService {

    public static final String WETHER_DETAILS_SERVICE = "WetherDetailsService";

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        String cityName = "";
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                cityName = bundle.getString(WConstants.CITY_SELECTED_NAME);
            }
        }

        IWDataProvider dataProvider = new WeatherDataProviderImpl(cityName);
        List<WeatherInfo> weatherInfos = dataProvider.getWeatherData();
        if (weatherInfos != null && weatherInfos.size() > 0) {
            WeatherInfo weatherInfo = weatherInfos.get(0);
            senDataToActivity(weatherInfo);
        }

    }

    private void senDataToActivity(WeatherInfo weatherInfo) {
        Intent responseIntent = new Intent();
        responseIntent.setAction(WConstants.SERVICE_CITY_WEATHER_RESPONSE);
        responseIntent.addCategory(Intent.CATEGORY_DEFAULT);
        responseIntent.putExtra(WConstants.CITY_WEATHER_DATA, weatherInfo);
        sendBroadcast(responseIntent);
    }

    public WeatherDetailsService() {
        super(WETHER_DETAILS_SERVICE);
    }
}
