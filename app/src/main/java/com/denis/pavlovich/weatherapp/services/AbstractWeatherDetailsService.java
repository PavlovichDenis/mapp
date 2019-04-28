package com.denis.pavlovich.weatherapp.services;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.denis.pavlovich.weatherapp.data.database.repository.WeatherInfoRepository;
import com.denis.pavlovich.weatherapp.entities.WeatherInfo;
import com.denis.pavlovich.weatherapp.utils.WConstants;

abstract class AbstractWeatherDetailsService extends IntentService {

    abstract void prepareParams(Intent intent);

    abstract WeatherInfo getWeatherData();

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        prepareParams(intent);
        WeatherInfo weatherInfo = getWeatherData();
        if (weatherInfo.getWeatherDate() != null) {
            // Удалось получить данные
            saveDataToDB(weatherInfo);
        }
        senDataToActivity(weatherInfo);
    }


    private void saveDataToDB(@NonNull WeatherInfo weatherInfo) {
        WeatherInfoRepository.getInstance().add(weatherInfo);
    }

    private void senDataToActivity(WeatherInfo weatherInfo) {
        Intent responseIntent = new Intent();
        responseIntent.setAction(WConstants.SERVICE_CITY_WEATHER_RESPONSE);
        responseIntent.addCategory(Intent.CATEGORY_DEFAULT);
        responseIntent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        responseIntent.putExtra(WConstants.CITY_WEATHER_DATA, weatherInfo);
        sendBroadcast(responseIntent);
    }

    public AbstractWeatherDetailsService(String name) {
        super(name);
    }
}
