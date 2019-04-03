package com.denis.pavlovich.weatherapp.data;

import android.content.res.Resources;
import android.support.annotation.NonNull;

import com.denis.pavlovich.weatherapp.R;
import com.denis.pavlovich.weatherapp.entities.WeatherInfo;

import java.io.Serializable;

public class WData implements Serializable {

    //показывать ветер
    private boolean showWind;

    //показывать влажность
    private boolean showHumidity;

    //показывать давление
    private boolean showPressure;

    private WeatherInfo weatherInfo;

    private int selectedIndex;

    public WData(boolean showWind, boolean showHumidity, boolean showPressure, @NonNull WeatherInfo weatherInfo, int selectedIndex) {
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

    private String getSR(Resources res, int id) {
        return res.getString(id) + " ";
    }

    public String getViewText(Resources res) {
        StringBuilder str = new StringBuilder();
        str.append(weatherInfo.getCity())
                .append(getLineSeparator());
        str.append(getSR(res, R.string.temperature))
                .append(weatherInfo.getTemperature())
                .append(" ")
                .append(weatherInfo.getTemperatureUnit())
                .append(getLineSeparator());
        str.append(getSR(res, R.string.precipitation))
                .append(weatherInfo.getPrecipitation())
                .append(getLineSeparator());
        if (showWind) {
            str.append(getSR(res, R.string.wind))
                    .append(weatherInfo.getWindDirection())
                    .append(" ")
                    .append(weatherInfo.getWindSpeed())
                    .append(" ")
                    .append(weatherInfo.getWindSpeedUnit())
                    .append(getLineSeparator());
        }
        if (showHumidity) {
            str.append(getSR(res, R.string.humidity))
                    .append(weatherInfo.getHumidity())
                    .append(" ")
                    .append(weatherInfo.getHumidityUnit())
                    .append(getLineSeparator());
        }
        if (showPressure) {
            str.append(getSR(res, R.string.pressure))
                    .append(weatherInfo.getPressure())
                    .append(" ")
                    .append(weatherInfo.getPressureUnit())
                    .append(getLineSeparator());
        }

        return str.toString();
    }


}
