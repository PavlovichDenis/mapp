package com.denis.pavlovich.weatherapp.data.database.repository;

import android.database.sqlite.SQLiteDatabase;

import com.denis.pavlovich.weatherapp.data.database.DataBaseProvider;

abstract class AbstractRepository<T> implements IRepository<T> {

    public SQLiteDatabase getDatabase() {
        DataBaseProvider dataBaseProvider = DataBaseProvider.getInstance();
        return dataBaseProvider.getWritableDatabase();
    }

}
