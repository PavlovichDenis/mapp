package com.denis.pavlovich.weatherapp.data.provider;

import com.denis.pavlovich.weatherapp.data.provider.rest.entities.WeatherResponseModel;
import com.denis.pavlovich.weatherapp.entities.City;
import com.denis.pavlovich.weatherapp.entities.WeatherInfo;

import java.util.List;

public class WeatherDataProviderImpl extends AbstractGetWeatherData {

    private City city;

    public WeatherDataProviderImpl(City city) {
        this.city = city;
    }

    @Override
    protected WeatherResponseModel getDataFromSource() {
        return WeatherDataLoader.getWeatherData(city.getName());
    }

    @Override
    public List<WeatherInfo> getWeatherData() {
        List<WeatherInfo> weatherIfs = super.getWeatherData();
        WeatherInfo weatherInfo = weatherIfs.get(0);
        weatherInfo.setCityId(city.getId());
        weatherInfo.setCity(city.getName());
        return weatherIfs;
    }
}
