package com.denis.pavlovich.weatherapp.dao;

import com.denis.pavlovich.weatherapp.entities.WeatherInfo;

import java.util.List;

/*
 * Интерфейс для доступа к данным о погоде
 *
 */
public interface WDataProvider {
    public List<WeatherInfo> getWeatherData();
}
