package com.denis.pavlovich.weatherapp.entities;

import android.content.res.Resources;

import com.denis.pavlovich.weatherapp.R;

import java.io.Serializable;

public class WeatherInfo implements Serializable {

    private String city;

    private String windDirection;

    private String windSpeed;

    private String windSpeedUnit;

    private String temperature;

    private String temperatureUnit;

    private String precipitation;

    private String pressure;

    private String pressureUnit;

    private String humidity;

    private String humidityUnit;

    private String url;


    public WeatherInfo() {
    }

    public String getCity() {
        return city;
    }

    public String getWindDirection() {
        return windDirection;
    }

    public String getWindSpeed() {
        return windSpeed;
    }

    public String getWindSpeedUnit() {
        return windSpeedUnit;
    }

    public String getTemperature() {
        return temperature;
    }

    public String getTemperatureUnit() {
        return temperatureUnit;
    }

    public String getPrecipitation() {
        return precipitation;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setWindDirection(String windDirection) {
        this.windDirection = windDirection;
    }

    public void setWindSpeed(String windSpeed) {
        this.windSpeed = windSpeed;
    }

    public void setWindSpeedUnit(String windSpeedUnit) {
        this.windSpeedUnit = windSpeedUnit;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public void setTemperatureUnit(String temperatureUnit) {
        this.temperatureUnit = temperatureUnit;
    }

    public void setPrecipitation(String precipitation) {
        this.precipitation = precipitation;
    }

    public String getPressure() {
        return pressure;
    }

    public String getPressureUnit() {
        return pressureUnit;
    }

    public String getHumidity() {
        return humidity;
    }

    public String getHumidityUnit() {
        return humidityUnit;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    public void setPressureUnit(String pressureUnit) {
        this.pressureUnit = pressureUnit;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public void setHumidityUnit(String humidityUnit) {
        this.humidityUnit = humidityUnit;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    private String getLineSeparator() {
        return System.getProperty("line.separator");
    }

    private String getStringWithspace(Resources res, int id) {
        return res.getString(id) + " ";
    }

    public String getViewText(Resources res) {
        StringBuilder str = new StringBuilder();
        str.append(getCity())
                .append(getLineSeparator());
        str.append(getStringWithspace(res, R.string.temperature))
                .append(getTemperature())
                .append(" ")
                .append(getTemperatureUnit())
                .append(getLineSeparator());
        str.append(getStringWithspace(res, R.string.precipitation))
                .append(getPrecipitation())
                .append(getLineSeparator());
        str.append(getStringWithspace(res, R.string.wind))
                .append(getWindDirection())
                .append(" ")
                .append(getWindSpeed())
                .append(" ")
                .append(getWindSpeedUnit())
                .append(getLineSeparator());
        str.append(getStringWithspace(res, R.string.humidity))
                .append(getHumidity())
                .append(" ")
                .append(getHumidityUnit())
                .append(getLineSeparator());
        str.append(getStringWithspace(res, R.string.pressure))
                .append(getPressure())
                .append(" ")
                .append(getPressureUnit())
                .append(getLineSeparator());
        return str.toString();
    }
}
