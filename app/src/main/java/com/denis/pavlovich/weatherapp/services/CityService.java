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
        senDataToActivity(new ParcelableObjectList<>(list));
    }

    private List<City> getCitiesList() {
        return CityRepository.getInstance().getAllList();
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
