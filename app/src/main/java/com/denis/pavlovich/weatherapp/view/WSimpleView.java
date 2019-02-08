package com.denis.pavlovich.weatherapp.view;

import android.support.annotation.NonNull;

import com.denis.pavlovich.weatherapp.entities.WeatherInfo;

import java.io.Serializable;

public class WSimpleView implements Serializable {

    //показывать ветер
    private boolean showWind;

    //показывать влажность
    private boolean showHumidity;

    //показывать давление
    private boolean showPressure;

    private WeatherInfo weatherInfo;

    private int selectedIndex;

    public WSimpleView(boolean showWind, boolean showHumidity, boolean showPressure, @NonNull WeatherInfo weatherInfo, @NonNull int selectedIndex) {
        this.showWind = showWind;
        this.showHumidity = showHumidity;
        this.showPressure = showPressure;
        this.weatherInfo = weatherInfo;
        this.selectedIndex = selectedIndex;
    }

    public boolean isShowWind() {

        return showWind;
    }

    public boolean isShowHumidity() {

        return showHumidity;
    }

    public boolean isShowPressure() {

        return showPressure;
    }

    public int getSelectedIndex() {

        return selectedIndex;
    }

    public WeatherInfo getWeatherInfo() {
        return weatherInfo;
    }

    private String getLineSeparator() {
        return System.getProperty("line.separator");
    }


    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append(weatherInfo.getCity())
                .append(getLineSeparator());
        str.append("Температура: ")
                .append(weatherInfo.getTemperature())
                .append(" ")
                .append(weatherInfo.getTemperatureUnit())
                .append(getLineSeparator());
        str.append("Осадки: ")
                .append(weatherInfo.getPrecipitation())
                .append(getLineSeparator());
        if (showWind) {
            str.append("Ветер: ")
                    .append(weatherInfo.getWindDirection())
                    .append(" ")
                    .append(weatherInfo.getWindSpeed())
                    .append(" ")
                    .append(weatherInfo.getWindSpeedUnit())
                    .append(getLineSeparator());
        }
        if (showHumidity) {
            str.append("Влажность: ")
                    .append(weatherInfo.getHumidity())
                    .append(" ")
                    .append(weatherInfo.getHumidityUnit())
                    .append(getLineSeparator());
        }
        if (showPressure) {
            str.append("Давление: ")
                    .append(weatherInfo.getPressure())
                    .append(" ")
                    .append(weatherInfo.getPressureUnit())
                    .append(getLineSeparator());
        }

        return str.toString();
    }
}
