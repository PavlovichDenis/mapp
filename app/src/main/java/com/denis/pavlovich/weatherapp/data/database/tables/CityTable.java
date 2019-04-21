package com.denis.pavlovich.weatherapp.data.database.tables;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.denis.pavlovich.weatherapp.entities.City;

import java.util.ArrayList;
import java.util.List;

public class CityTable {

    private final static String TABLE_NAME = "Cities";
    private final static String COLUMN_ID = "id";
    private final static String COLUMN_CITY_NAME = "name";

    public static void createTable(SQLiteDatabase database) {
        database.execSQL("CREATE TABLE " + TABLE_NAME + " (" + COLUMN_ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_CITY_NAME + " TEXT);");
        String[] cities = {"Москва", "Санкт-Петербург", "Ярославль", "Новосибирск", "Казань", "Сургут", "Омск"};
        for (String cityName : cities) {
            addCity(new City(null, cityName), database);
        }
    }

    public static void addCity(City city, SQLiteDatabase database) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_CITY_NAME, city.getName());
        database.insert(TABLE_NAME, null, values);
    }

    public static List<City> getAllCities(SQLiteDatabase database) {
        Cursor cursor = database.query(TABLE_NAME, null, null, null, null, null, COLUMN_CITY_NAME);
        return getResultFromCursor(cursor);
    }

    public static void deleteCity(Long cityId, SQLiteDatabase database) {
        database.delete(TABLE_NAME, COLUMN_ID + " = " + cityId, null);
    }


    private static List<City> getResultFromCursor(Cursor cursor) {
        List<City> result = null;
        if (cursor != null && cursor.moveToFirst()) {
            result = new ArrayList<>(cursor.getCount());
            int nameIdx = cursor.getColumnIndex(COLUMN_CITY_NAME);
            int idIdx = cursor.getColumnIndex(COLUMN_ID);
            do {
                Long id = cursor.getLong(idIdx);
                String name = cursor.getString(nameIdx);
                City city = new City(id, name);
                result.add(city);
            } while (cursor.moveToNext());
        }
        try {
            if (cursor != null) {
                cursor.close();
            }
        } catch (Exception ignored) {
        }
        return result == null ? new ArrayList<City>(0) : result;
    }
}
