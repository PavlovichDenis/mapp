package com.denis.pavlovich.weatherapp.services;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

import com.denis.pavlovich.weatherapp.R;
import com.denis.pavlovich.weatherapp.application.WeatherApplication;
import com.denis.pavlovich.weatherapp.data.provider.IWDataProvider;
import com.denis.pavlovich.weatherapp.data.provider.WeatherDataProviderImpl;
import com.denis.pavlovich.weatherapp.entities.City;
import com.denis.pavlovich.weatherapp.entities.WeatherInfo;

public class FavoriteCityWeather extends AsyncTask<City, Void, WeatherInfo> {

    private final static String CHANNEL_ID = "WEATHER_CHANNEL";

    @Override
    protected WeatherInfo doInBackground(City... cities) {
        City city = cities[0];
        if (city != null && city.getId() != null) {
            IWDataProvider dataProvider = new WeatherDataProviderImpl(city);
            return dataProvider.getWeatherData().get(0);
        }
        return null;
    }

    // Вывод уведомления в строке состояния
    private void makeNote(String message) {
        final Context context = WeatherApplication.getContext();
        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = notificationManager.getNotificationChannel(CHANNEL_ID);
            if (notificationChannel == null) {
                int importance = NotificationManager.IMPORTANCE_HIGH;
                notificationChannel = new NotificationChannel(CHANNEL_ID, context.getString(R.string.channelName), importance);
                notificationChannel.setLightColor(Color.GREEN);
                notificationChannel.enableVibration(true);
                notificationManager.createNotificationChannel(notificationChannel);
            }
            builder = new NotificationCompat.Builder(context, CHANNEL_ID);
        } else {
            builder = new NotificationCompat.Builder(context);
        }

        builder
                .setSmallIcon(R.drawable.ic_weather)
                .setContentTitle(context.getString(R.string.favoriteWeather))
                .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
                .setContentText(message);
        Intent resultIntent = new Intent();
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(
                0,
                PendingIntent.FLAG_UPDATE_CURRENT
        );
        builder.setContentIntent(resultPendingIntent);
        notificationManager.notify(0, builder.build());
    }

    @Override
    protected void onPostExecute(WeatherInfo weatherInfo) {
        super.onPostExecute(weatherInfo);
        // тут выводим данные пользователю
        if (weatherInfo != null) {
            makeNote(weatherInfo.getViewText(WeatherApplication.getContext().getResources()));
        }
    }
}
