package com.denis.pavlovich.weatherapp.data.database.repository;

import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import com.denis.pavlovich.weatherapp.data.database.tables.CityTable;
import com.denis.pavlovich.weatherapp.entities.City;

import java.util.List;

public class CityRepository extends AbstractRepository<City> {

    private static CityRepository cityRepository = null;

    private CityRepository() {
    }

    public static CityRepository getInstance() {
        if (cityRepository == null) {
            cityRepository = new CityRepository();
        }
        return cityRepository;
    }

    @Override
    public void delete(@NonNull City city) {
        SQLiteDatabase db = getDatabase();
        CityTable.deleteCity(city.getId(), db);
    }

    @Override
    public void add(@NonNull City obj) {
        SQLiteDatabase db = getDatabase();
        CityTable.addCity(obj, db);
    }

    @Override
    public void edit(@NonNull City obj) {
       throw new UnsupportedOperationException();
    }

    public List<City> getAllCities() {
        return CityTable.getAllCities(getDatabase());
    }

}
