package com.denis.pavlovich.weatherapp.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.denis.pavlovich.weatherapp.data.database.repository.CityRepository;
import com.denis.pavlovich.weatherapp.entities.City;
import com.denis.pavlovich.weatherapp.services.FavoriteCityWeather;
import com.denis.pavlovich.weatherapp.utils.WConstants;

import static android.content.Context.MODE_PRIVATE;

public class TimeBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action != null && action.equals(Intent.ACTION_TIME_TICK)) {
            SharedPreferences sharedPreferences = context.getSharedPreferences(
                    WConstants.APP_PREFERENCES, MODE_PRIVATE);
            Long cityId = sharedPreferences.getLong(WConstants.FAVORITE_CITY_ID, 0);
            City city = CityRepository.getInstance().getById(cityId);
            if (city == null) {
                return;
            }
            //   Здесь я буду запускать сервис для определения погоды по favorite city
            FavoriteCityWeather service = new FavoriteCityWeather();
            service.execute(city);
        }
    }
}
