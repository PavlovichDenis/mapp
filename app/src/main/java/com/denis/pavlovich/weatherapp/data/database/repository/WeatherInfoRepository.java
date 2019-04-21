package com.denis.pavlovich.weatherapp.data.database.repository;

import android.support.annotation.NonNull;

import com.denis.pavlovich.weatherapp.data.database.tables.WeatherInfoTable;
import com.denis.pavlovich.weatherapp.entities.WeatherInfo;


public class WeatherInfoRepository extends AbstractRepository<WeatherInfo> {

    private static WeatherInfoRepository weatherInfoRepository;

    public static WeatherInfoRepository getInstance() {
        if (weatherInfoRepository == null) {
            weatherInfoRepository = new WeatherInfoRepository();
        }
        return weatherInfoRepository;
    }

    private WeatherInfoRepository() {
    }

    @Override
    public void delete(@NonNull WeatherInfo obj) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void add(@NonNull WeatherInfo obj) {
        WeatherInfoTable.addWeatherInfo(obj, getDatabase());
    }

    @Override
    public void edit(@NonNull WeatherInfo obj) {
        throw new UnsupportedOperationException();
    }

    /*public List<WeatherInfo> getAllWeatherByCityId(@NonNull City city) {
        return WeatherInfoTable.getAllWeatherByCityId(city.getId(), getDatabase());
    }*/
}
