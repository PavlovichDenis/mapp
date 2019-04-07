package com.denis.pavlovich.weatherapp.services;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.denis.pavlovich.weatherapp.data.provider.IWDataProvider;
import com.denis.pavlovich.weatherapp.data.provider.WResourceDataProviderImpl;
import com.denis.pavlovich.weatherapp.entities.WeatherInfo;
import com.denis.pavlovich.weatherapp.utils.WConstants;

import java.util.List;

public class WeatherDetailsService extends IntentService {

    public static final String WETHER_DETAILS_SERVICE = "WetherDetailsService";

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        int cityIndex = 1;
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                cityIndex = bundle.getInt(WConstants.CITY_SELECTED);
            }
        }
        // специально сделал, чтобы было видно ProgressBar
        delay();
        IWDataProvider dataProvider = new WResourceDataProviderImpl(getResources());
        List<WeatherInfo> weatherInfos = dataProvider.getWeatherData();
        if (weatherInfos != null && weatherInfos.size() >= cityIndex) {
            WeatherInfo weatherInfo = weatherInfos.get(cityIndex);
            senDataToActivity(weatherInfo);
        }
    }

    private void delay() {
        try {
            Thread.sleep(2500);
        } catch (InterruptedException e) {
            e.printStackTrace();
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
