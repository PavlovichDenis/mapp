package com.denis.pavlovich.weatherapp.data.provider;

import android.support.annotation.Nullable;

import com.denis.pavlovich.weatherapp.data.provider.rest.IOpenWeather;
import com.denis.pavlovich.weatherapp.data.provider.rest.OpenWeatherAPI;
import com.denis.pavlovich.weatherapp.data.provider.rest.entities.WeatherResponseModel;

import java.util.Locale;

import retrofit2.Response;


class WeatherDataLoader {
    private static final String OPEN_WEATHER_API_KEY = "f3f2763fe63803beef4851d6365c83bc";
    private static final String METRIC = "metric";
    private static final int ALL_GOOD = 200;

    @Nullable
    static WeatherResponseModel getWeatherData(String city) {
        try {
            String lang = Locale.getDefault().getLanguage();
            IOpenWeather openWeatherAPI = OpenWeatherAPI.getInstance().getApi();
            // использую execute, т.к. уже нахожусь в отдельном потоке
            Response<WeatherResponseModel> response =
                    openWeatherAPI.loadWeather(city,
                            OPEN_WEATHER_API_KEY,
                            lang, METRIC).execute();
            if (response.isSuccessful()) {
                WeatherResponseModel weatherModel = response.body();
                if (weatherModel != null && weatherModel.getCod() == ALL_GOOD) {
                    return weatherModel;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
