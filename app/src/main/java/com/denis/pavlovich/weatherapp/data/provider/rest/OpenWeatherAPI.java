package com.denis.pavlovich.weatherapp.data.provider.rest;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OpenWeatherAPI {

    private static final String HTTP_API_OPENWEATHERMAP_ORG = "http://api.openweathermap.org/";

    private static OpenWeatherAPI instance = null;

    private IOpenWeather openWeatherAPI;

    private OpenWeatherAPI() {
        openWeatherAPI = createApiInstance();
    }

    public static OpenWeatherAPI getInstance() {
        if (instance == null) {
            instance = new OpenWeatherAPI();
        }
        return instance;
    }

    public IOpenWeather getApi() {
        return openWeatherAPI;
    }

    private IOpenWeather createApiInstance() {
        Retrofit adapter = new Retrofit.Builder()
                .baseUrl(HTTP_API_OPENWEATHERMAP_ORG)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return adapter.create(IOpenWeather.class);
    }
}
