package com.denis.pavlovich.weatherapp.entities;

import android.content.res.Resources;
import android.os.Parcel;
import android.os.Parcelable;

import com.denis.pavlovich.weatherapp.R;

import java.util.Date;

public class WeatherInfo implements Parcelable {

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

    private Long id;

    private Long cityId;

    private Date weatherDate;

    public WeatherInfo() {
    }

    private WeatherInfo(Parcel in) {
        String[] data = new String[12];
        in.readStringArray(data);
        city = data[0];
        windDirection = data[1];
        windSpeed = data[2];
        windSpeedUnit = data[3];
        temperature = data[4];
        temperatureUnit = data[5];
        precipitation = data[6];
        pressure = data[7];
        pressureUnit = data[8];
        humidity = data[9];
        humidityUnit = data[10];
        url = data[11];
        weatherDate = new Date(in.readLong());
        id = in.readLong();
        cityId = in.readLong();

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public Date getWeatherDate() {
        return weatherDate;
    }

    public void setWeatherDate(Date weatherDate) {
        this.weatherDate = weatherDate;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[]{
                city,
                windDirection,
                windSpeed,
                windSpeedUnit,
                temperature,
                temperatureUnit,
                precipitation,
                pressure,
                pressureUnit,
                humidity,
                humidityUnit,
                url
        });
        if (weatherDate != null) {
            dest.writeLong(weatherDate.getTime());
        }
        if (id != null) {
            dest.writeLong(id);
        }
        if (cityId != null) {
            dest.writeLong(cityId);
        }
    }

    public static final Parcelable.Creator<WeatherInfo> CREATOR = new Parcelable.Creator<WeatherInfo>() {

        @Override
        public WeatherInfo createFromParcel(Parcel source) {
            return new WeatherInfo(source);
        }

        @Override
        public WeatherInfo[] newArray(int size) {
            return new WeatherInfo[size];
        }
    };

}
