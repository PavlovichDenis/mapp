package com.denis.pavlovich.weatherapp.data.provider;

import com.denis.pavlovich.weatherapp.entities.WeatherInfo;

import java.util.List;

/*
 * Интерфейс для доступа к данным о погоде
 *
 */
public interface IWDataProvider {
    List<WeatherInfo> getWeatherData();
}
