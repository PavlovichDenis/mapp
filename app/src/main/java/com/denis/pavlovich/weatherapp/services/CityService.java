package com.denis.pavlovich.weatherapp.services;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.denis.pavlovich.weatherapp.data.database.repository.CityRepository;
import com.denis.pavlovich.weatherapp.entities.City;
import com.denis.pavlovich.weatherapp.utils.WConstants;

import java.util.List;

public class CityService extends IntentService {

    public static final String WEATHER_CITY_SERVICE = "WeatherCityService";

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        //Здесь будем получать список городов. Возможно даже с фильтром
        List<City> list = getCitiesList();
        // специально сделал, чтобы было видно ProgressBar
        delay();
        // Возхвращаем результат
        senDataToActivity(new ParcelableObjectList<>(list));
    }

    private void delay() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private List<City> getCitiesList() {
        return CityRepository.getInstance().getAllCities();
    }

    private void senDataToActivity(ParcelableObjectList<City> objects) {
        Intent responseIntent = new Intent();
        responseIntent.setAction(WConstants.SERVICE_CITY_RESPONSE);
        responseIntent.addCategory(Intent.CATEGORY_DEFAULT);
        responseIntent.putExtra(WConstants.CITIES_LIST, objects);
        sendBroadcast(responseIntent);
    }

    public CityService() {
        super(WEATHER_CITY_SERVICE);
    }
}
