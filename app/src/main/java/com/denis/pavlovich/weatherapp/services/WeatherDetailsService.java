package com.denis.pavlovich.weatherapp.services;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.denis.pavlovich.weatherapp.data.database.repository.WeatherInfoRepository;
import com.denis.pavlovich.weatherapp.data.provider.IWDataProvider;
import com.denis.pavlovich.weatherapp.data.provider.WeatherDataProviderImpl;
import com.denis.pavlovich.weatherapp.entities.City;
import com.denis.pavlovich.weatherapp.entities.WeatherInfo;
import com.denis.pavlovich.weatherapp.utils.WConstants;

import java.util.List;

public class WeatherDetailsService extends IntentService {

    public static final String WEATHER_DETAILS_SERVICE = "WeatherDetailsService";

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        City city = null;
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                city = bundle.getParcelable(WConstants.CITY_SELECTED);
            }
        }

        IWDataProvider dataProvider = new WeatherDataProviderImpl(city);
        List<WeatherInfo> weatherInfos = dataProvider.getWeatherData();
        if (weatherInfos != null && weatherInfos.size() > 0) {
            WeatherInfo weatherInfo = weatherInfos.get(0);
            if (weatherInfo.getWeatherDate() != null) {
                // Удалось получить данные
                saveDataToDB(weatherInfo);
            }
            senDataToActivity(weatherInfo);
        }
    }

    private void saveDataToDB(@NonNull WeatherInfo weatherInfo) {
        WeatherInfoRepository.getInstance().add(weatherInfo);
    }

    private void senDataToActivity(WeatherInfo weatherInfo) {
        Intent responseIntent = new Intent();
        responseIntent.setAction(WConstants.SERVICE_CITY_WEATHER_RESPONSE);
        responseIntent.addCategory(Intent.CATEGORY_DEFAULT);
        responseIntent.putExtra(WConstants.CITY_WEATHER_DATA, weatherInfo);
        sendBroadcast(responseIntent);
    }

    public WeatherDetailsService() {
        super(WEATHER_DETAILS_SERVICE);
    }
}
