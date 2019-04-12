package com.denis.pavlovich.weatherapp.data.provider;


import android.content.Context;

import com.denis.pavlovich.weatherapp.R;
import com.denis.pavlovich.weatherapp.application.WeatherApplication;
import com.denis.pavlovich.weatherapp.entities.WeatherInfo;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class WeatherDataProviderImpl implements IWDataProvider {

    private String city;

    public WeatherDataProviderImpl(String city) {
        this.city = city;
    }

    @Override
    public List<WeatherInfo> getWeatherData() {
        List<WeatherInfo> weatherInfos = new ArrayList<>();
        JSONObject data = WeatherDataLoader.getJSONData(city);
        WeatherInfo weatherInfo = processJSONData(data);
        weatherInfo.setCity(city);
        weatherInfos.add(weatherInfo);
        return weatherInfos;
    }

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

    private WeatherInfo processJSONData(JSONObject data) {
        WeatherInfo weatherInfo = new WeatherInfo();
        if (data != null) {
            try {
                Context context = WeatherApplication.getContext();
                JSONObject main = data.getJSONObject("main");
                weatherInfo.setTemperature(String.format(Locale.getDefault(), "%.2f", main.getDouble("temp")));
                weatherInfo.setTemperatureUnit("\u2103");
                weatherInfo.setHumidity(main.getString("humidity"));
                weatherInfo.setHumidityUnit("%");
                double pressure = main.getDouble("pressure");
                pressure = Math.round(pressure * 0.750063755419211);
                weatherInfo.setPressure(String.valueOf(pressure));
                weatherInfo.setPressureUnit(context.getString(R.string.pressureUnit));
                JSONObject wind = data.getJSONObject("wind");
                int deg = wind.getInt("deg");
                weatherInfo.setWindSpeedUnit(context.getString(R.string.speedUnit));
                weatherInfo.setWindSpeed(wind.getString("speed"));
                weatherInfo.setWindDirection(getWindDirection(deg, context));
                JSONObject weather = data.getJSONArray("weather").getJSONObject(0);
                weatherInfo.setPrecipitation(weather.getString("description"));
                weatherInfo.setUrl("https://www.gismeteo.ru/");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return weatherInfo;
    }
}
