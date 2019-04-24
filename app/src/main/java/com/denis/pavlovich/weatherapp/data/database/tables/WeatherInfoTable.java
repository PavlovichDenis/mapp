package com.denis.pavlovich.weatherapp.data.database.tables;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.denis.pavlovich.weatherapp.entities.WeatherInfo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class WeatherInfoTable {

    private final static String TABLE_NAME = "WeatherInfo";
    private final static String COLUMN_ID = "id";
    private final static String COLUMN_CITY_ID = "cityId";
    private final static String COLUMN_WIND_DIRECTION = "windDirection";
    private final static String COLUMN_WIND_SPEED = "windSpeed";
    private final static String COLUMN_WIND_SPEED_UNIT = "windSpeedUnit";
    private final static String COLUMN_TEMPERATURE = "temperature";
    private final static String COLUMN_TEMPERATURE_UNIT = "temperatureUnit";
    private final static String COLUMN_PRECIPITATION = "precipitation";
    private final static String COLUMN_PRESSURE = "pressure";
    private final static String COLUMN_PRESSURE_UNIT = "pressureUnit";
    private final static String COLUMN_HUMIDITY = "humidity";
    private final static String COLUMN_HUMITITY_UNIT = "humidityUnit";
    private final static String COLUMN_DATE = "weatherDate";


    public static void createTable(SQLiteDatabase database) {
        database.execSQL("CREATE TABLE " + TABLE_NAME + " (" + COLUMN_ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_CITY_ID + " INTEGER ,"
                + COLUMN_DATE + " LONG,"
                + COLUMN_WIND_DIRECTION + " TEXT,"
                + COLUMN_WIND_SPEED + " TEXT,"
                + COLUMN_WIND_SPEED_UNIT + " TEXT,"
                + COLUMN_TEMPERATURE + " TEXT,"
                + COLUMN_TEMPERATURE_UNIT + " TEXT,"
                + COLUMN_PRECIPITATION + " TEXT,"
                + COLUMN_PRESSURE + " TEXT,"
                + COLUMN_PRESSURE_UNIT + " TEXT,"
                + COLUMN_HUMIDITY + " TEXT,"
                + COLUMN_HUMITITY_UNIT + " TEXT );");
    }

    public static void addWeatherInfo(WeatherInfo weatherInfo, SQLiteDatabase database) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_WIND_DIRECTION, weatherInfo.getWindDirection());
        values.put(COLUMN_CITY_ID, weatherInfo.getCityId());
        values.put(COLUMN_WIND_SPEED, weatherInfo.getWindSpeed());
        values.put(COLUMN_WIND_SPEED_UNIT, weatherInfo.getWindSpeedUnit());
        values.put(COLUMN_TEMPERATURE, weatherInfo.getTemperature());
        values.put(COLUMN_TEMPERATURE_UNIT, weatherInfo.getTemperatureUnit());
        values.put(COLUMN_PRECIPITATION, weatherInfo.getPrecipitation());
        values.put(COLUMN_PRESSURE, weatherInfo.getPressure());
        values.put(COLUMN_PRESSURE_UNIT, weatherInfo.getPressureUnit());
        values.put(COLUMN_HUMIDITY, weatherInfo.getHumidity());
        values.put(COLUMN_HUMITITY_UNIT, weatherInfo.getHumidityUnit());
        values.put(COLUMN_DATE, weatherInfo.getWeatherDate().getTime());
        Long id = database.insert(TABLE_NAME, null, values);
        weatherInfo.setId(id);
    }

    public static void onUpgrade(SQLiteDatabase database) {
        database.execSQL("CREATE INDEX IF NOT EXISTS cityIdIdx on  " + TABLE_NAME + "(" + COLUMN_CITY_ID + ")");
    }

    public static List<WeatherInfo> getAllWeatherByCityId(Long cityId, SQLiteDatabase database) {
        String[] args = {String.valueOf(cityId)};
        Cursor cursor = database.query(TABLE_NAME, null, COLUMN_CITY_ID + " = ?", args, null, null, COLUMN_DATE);
        return getResultFromCursor(cursor);
    }

    private static List<WeatherInfo> getResultFromCursor(Cursor cursor) {
        List<WeatherInfo> result = null;
        if (cursor != null && cursor.moveToFirst()) {
            result = new ArrayList<>(cursor.getCount());
            int columnId = cursor.getColumnIndex(COLUMN_ID);
            int cityId = cursor.getColumnIndex(COLUMN_CITY_ID);
            int windDirection = cursor.getColumnIndex(COLUMN_WIND_DIRECTION);
            int windSpeed = cursor.getColumnIndex(COLUMN_WIND_SPEED);
            int windSpeedUnit = cursor.getColumnIndex(COLUMN_WIND_SPEED_UNIT);
            int temperature = cursor.getColumnIndex(COLUMN_TEMPERATURE);
            int temperatureUnit = cursor.getColumnIndex(COLUMN_TEMPERATURE_UNIT);
            int precipitation = cursor.getColumnIndex(COLUMN_PRECIPITATION);
            int pressure = cursor.getColumnIndex(COLUMN_PRESSURE);
            int pressureUnit = cursor.getColumnIndex(COLUMN_PRESSURE_UNIT);
            int humidity = cursor.getColumnIndex(COLUMN_HUMIDITY);
            int humidityUnit = cursor.getColumnIndex(COLUMN_HUMITITY_UNIT);
            int weatherDate = cursor.getColumnIndex(COLUMN_DATE);
            do {
                WeatherInfo weatherInfo = new WeatherInfo();
                weatherInfo.setId(cursor.getLong(columnId));
                weatherInfo.setCityId(cursor.getLong(cityId));
                weatherInfo.setWindDirection(cursor.getString(windDirection));
                weatherInfo.setWindSpeed(cursor.getString(windSpeed));
                weatherInfo.setWindSpeedUnit(cursor.getString(windSpeedUnit));
                weatherInfo.setTemperature(cursor.getString(temperature));
                weatherInfo.setTemperatureUnit(cursor.getString(temperatureUnit));
                weatherInfo.setPrecipitation(cursor.getString(precipitation));
                weatherInfo.setPressure(cursor.getString(pressure));
                weatherInfo.setPressureUnit(cursor.getString(pressureUnit));
                weatherInfo.setHumidity(cursor.getString(humidity));
                weatherInfo.setHumidityUnit(cursor.getString(humidityUnit));
                weatherInfo.setWeatherDate(new Date(cursor.getLong(weatherDate)));
            } while (cursor.moveToNext());
        }
        try {
            if (cursor != null) {
                cursor.close();
            }
        } catch (Exception ignored) {
        }
        return result == null ? new ArrayList<WeatherInfo>(0) : result;
    }

}
