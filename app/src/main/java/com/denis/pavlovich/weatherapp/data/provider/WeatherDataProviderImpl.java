package com.denis.pavlovich.weatherapp.data.provider;


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

    private String getWindDirection(int deg) {
        if (deg == 0) {
            return "нет";
        } else if (deg > 0 && deg < 90) {
            return "юв";
        } else if (deg == 90) {
            return "ю";
        } else if (deg > 90 && deg < 180) {
            return "юз";
        } else if (deg == 180) {
            return "з";
        } else if (deg > 180 && deg < 270) {
            return "сз";
        } else if (deg == 270) {
            return "c";
        } else if (deg > 270 && deg < 360) {
            return "св";
        } else if (deg == 360) {
            return "в";
        } else return "";
    }

    private WeatherInfo processJSONData(JSONObject data) {
        WeatherInfo weatherInfo = new WeatherInfo();
        if (data != null) {
            try {
                JSONObject main = data.getJSONObject("main");
                weatherInfo.setTemperature(String.format(Locale.getDefault(), "%.2f", main.getDouble("temp")));
                weatherInfo.setTemperatureUnit("\u2103");
                weatherInfo.setHumidity(main.getString("humidity"));
                weatherInfo.setHumidityUnit("%");
                double pressure = main.getDouble("pressure");
                pressure = Math.round(pressure * 0.750063755419211);
                weatherInfo.setPressure(String.valueOf(pressure));
                weatherInfo.setPressureUnit("мм рт.ст.");
                JSONObject wind = data.getJSONObject("wind");
                int deg = wind.getInt("deg");
                weatherInfo.setWindSpeedUnit("м/c");
                weatherInfo.setWindSpeed(wind.getString("speed"));
                weatherInfo.setWindDirection(getWindDirection(deg));
                weatherInfo.setPrecipitation(""); //осадки в JSON не нашел
                weatherInfo.setUrl("https://www.gismeteo.ru/");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return weatherInfo;
    }
}
