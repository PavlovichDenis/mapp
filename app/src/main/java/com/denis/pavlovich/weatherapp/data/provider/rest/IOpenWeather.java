package com.denis.pavlovich.weatherapp.data.provider.rest;

import com.denis.pavlovich.weatherapp.data.provider.rest.entities.WeatherResponseModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IOpenWeather {

    @GET("data/2.5/weather")
    Call<WeatherResponseModel> loadWeather(@Query("q") String city,
                                           @Query("appid") String keyApi,
                                           @Query("lang") String lang,
                                           @Query("units") String units);
}