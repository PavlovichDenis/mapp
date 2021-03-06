package com.denis.pavlovich.weatherapp.data.provider;

import android.content.Context;

import com.denis.pavlovich.weatherapp.R;
import com.denis.pavlovich.weatherapp.application.WeatherApplication;
import com.denis.pavlovich.weatherapp.data.provider.rest.entities.MainModel;
import com.denis.pavlovich.weatherapp.data.provider.rest.entities.WeatherModel;
import com.denis.pavlovich.weatherapp.data.provider.rest.entities.WeatherResponseModel;
import com.denis.pavlovich.weatherapp.data.provider.rest.entities.WindModel;
import com.denis.pavlovich.weatherapp.entities.WeatherInfo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public abstract class AbstractGetWeatherData implements IWDataProvider {

    protected abstract WeatherResponseModel getDataFromSource();

    private String getWindDirection(int deg, Context context) {
        if (deg == 0) {
            return context.getString(R.string.windNo);
        } else if (deg > 0 && deg < 90) {
            return context.getString(R.string.windSouthEast);
        } else if (deg == 90) {
            return context.getString(R.string.windSouth);
        } else if (deg > 90 && deg < 180) {
            return context.getString(R.string.windSouthWest);
        } else if (deg == 180) {
            return context.getString(R.string.windWest);
        } else if (deg > 180 && deg < 270) {
            return context.getString(R.string.windNorthWest);
        } else if (deg == 270) {
            return context.getString(R.string.windNorth);
        } else if (deg > 270 && deg < 360) {
            return context.getString(R.string.windNorthEast);
        } else if (deg == 360) {
            return context.getString(R.string.windEast);
        } else return "";
    }

    private WeatherInfo processData(WeatherResponseModel data) {
        WeatherInfo weatherInfo = new WeatherInfo();
        if (data != null) {
            Context context = WeatherApplication.getContext();
            MainModel main = data.getMain();
            weatherInfo.setTemperature(String.format(Locale.getDefault(), "%.2f", main.getTemp()));
            weatherInfo.setTemperatureUnit("\u2103");
            weatherInfo.setHumidity(String.valueOf(main.getHumidity()));
            weatherInfo.setHumidityUnit("%");
            double pressure = main.getPressure();
            pressure = Math.round(pressure * 0.750063755419211);
            weatherInfo.setPressure(String.valueOf(pressure));
            weatherInfo.setPressureUnit(context.getString(R.string.pressureUnit));
            WindModel wind = data.getWind();
            int deg = Math.round(wind.getDeg());
            weatherInfo.setWindSpeedUnit(context.getString(R.string.speedUnit));
            weatherInfo.setWindSpeed(String.valueOf(wind.getSpeed()));
            weatherInfo.setWindDirection(getWindDirection(deg, context));
            WeatherModel weather = data.getWeather()[0];
            weatherInfo.setPrecipitation(weather.getDescription());
            weatherInfo.setWeatherDate(new Date());
            weatherInfo.setUrl("https://www.gismeteo.ru/");
            weatherInfo.setCity(data.getName());
        }
        return weatherInfo;
    }

    @Override
    public List<WeatherInfo> getWeatherData() {
        List<WeatherInfo> weatherIfs = new ArrayList<>();
        WeatherResponseModel data = getDataFromSource();
        WeatherInfo weatherInfo = processData(data);
        weatherIfs.add(weatherInfo);
        return weatherIfs;
    }
}
