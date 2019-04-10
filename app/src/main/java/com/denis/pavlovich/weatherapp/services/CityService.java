package com.denis.pavlovich.weatherapp.services;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.denis.pavlovich.weatherapp.data.provider.IWDataProvider;
import com.denis.pavlovich.weatherapp.data.provider.WResourceDataProviderImpl;
import com.denis.pavlovich.weatherapp.entities.WeatherInfo;
import com.denis.pavlovich.weatherapp.utils.WConstants;

import java.util.ArrayList;
import java.util.List;

public class CityService extends IntentService {

    public static final String WEATHER_CITY_SERVICE = "WeatherCityService";

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        //Здесь будем получать список городов. Возможно даже с фильтром
        ArrayList<String> list = getCitiesList();
        // специально сделал, чтобы было видно ProgressBar
        delay();
        // Возхвращаем результат
        senDataToActivity(list);
    }

    private void delay() {
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private ArrayList<String> getCitiesList() {
        IWDataProvider dataProvider = new WResourceDataProviderImpl(getResources());
        List<WeatherInfo> weatherInfos = dataProvider.getWeatherData();
        ArrayList<String> list = new ArrayList<>();
        for (WeatherInfo wi : weatherInfos) {
            list.add(wi.getCity());
        }
        return list;
    }

    private void senDataToActivity(ArrayList<String> list) {
        Intent responseIntent = new Intent();
        responseIntent.setAction(WConstants.SERVICE_CITY_RESPONSE);
        responseIntent.addCategory(Intent.CATEGORY_DEFAULT);
        responseIntent.putExtra(WConstants.CITIES_LIST, list);
        sendBroadcast(responseIntent);
    }

    public CityService() {
        super(WEATHER_CITY_SERVICE);
    }
}
