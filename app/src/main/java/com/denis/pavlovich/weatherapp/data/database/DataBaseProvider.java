package com.denis.pavlovich.weatherapp.data.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.denis.pavlovich.weatherapp.application.WeatherApplication;
import com.denis.pavlovich.weatherapp.data.database.tables.CityTable;
import com.denis.pavlovich.weatherapp.data.database.tables.WeatherInfoTable;

public class DataBaseProvider extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "weatherApplication.db";
    private static final int DATABASE_VERSION = 2;

    private static DataBaseProvider dataBaseProvider;

    private DataBaseProvider(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static DataBaseProvider getInstance() {
        if (dataBaseProvider == null) {
            dataBaseProvider = new DataBaseProvider(WeatherApplication.getContext());
        }
        return dataBaseProvider;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        CityTable.createTable(db);
        WeatherInfoTable.createTable(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            CityTable.onUpgrade(db);
            WeatherInfoTable.onUpgrade(db);
        }
    }
}
