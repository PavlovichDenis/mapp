package com.denis.pavlovich.weatherapp;

import android.database.sqlite.SQLiteDatabase;
import android.support.test.runner.AndroidJUnit4;

import com.denis.pavlovich.weatherapp.data.database.DataBaseProvider;
import com.denis.pavlovich.weatherapp.data.database.tables.WeatherInfoTable;
import com.denis.pavlovich.weatherapp.entities.WeatherInfo;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class DatabaseWeatherInfoTest {
    @Test
    public void useAppContext() {
        // TODO
        DataBaseProvider dataBaseProvider = DataBaseProvider.getInstance();
        SQLiteDatabase database = dataBaseProvider.getReadableDatabase();
        List<WeatherInfo> info = WeatherInfoTable.getAllWeatherByCityId(1L, database);
        assertTrue(info.size() > 0);
    }
}
