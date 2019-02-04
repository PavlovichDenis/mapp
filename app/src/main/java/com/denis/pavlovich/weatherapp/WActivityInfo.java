package com.denis.pavlovich.weatherapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.denis.pavlovich.weatherapp.entities.WeatherInfo;
import com.denis.pavlovich.weatherapp.utils.WConstants;
import com.denis.pavlovich.weatherapp.utils.WLogging;
import com.denis.pavlovich.weatherapp.view.WSimpleView;


public class WActivityInfo extends WAbstractActivityWithThemeSupport {


    View.OnClickListener sendFriendClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent sendIntent = new Intent(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, getWeatherData().toString());
            sendIntent.setType(WConstants.TEXT_PLAIN);
            processIntent(sendIntent);
        }
    };

    private void processIntent(Intent intent) {
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            WLogging.makeToast(getBaseContext(), getResources().getString(R.string.noViewApp));
        }
    }

    View.OnClickListener lookWeatherInInternetListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            Uri uri = Uri.parse(getWeatherData().getWeatherInfo().getUrl());
            intent.setData(uri);
            processIntent(intent);

        }
    };

    private void setVisible(View view, boolean visible) {
        if (visible) {
            view.setVisibility(View.VISIBLE);
        } else {
            view.setVisibility(View.GONE);
        }
    }

    private String getSpace() {
        return getResources().getString(R.string.space);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setTheme(getApplicationTheme());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_winfo);
        TextView textView = findViewById(R.id.cityName);
        WSimpleView simpleView = getWeatherData();
        WeatherInfo weatherInfo = simpleView.getWeatherInfo();
        textView.setText(weatherInfo.getCity());
        // устанавлиаем идимость элементов
        setVisible(findViewById(R.id.windView), simpleView.isShowWind());
        setVisible(findViewById(R.id.pressureView), simpleView.isShowPressure());
        setVisible(findViewById(R.id.humidityView), simpleView.isShowHumidity());

        //Устанавлиаем сведения о погоде
        textView = findViewById(R.id.temperatureValue);
        textView.setText(weatherInfo.getTemperature() + getSpace() + weatherInfo.getTemperatureUnit());
        textView = findViewById(R.id.windValue);
        textView.setText(weatherInfo.getWindDirection() + getSpace() + weatherInfo.getWindSpeed() + getSpace() + weatherInfo.getWindSpeedUnit());
        textView = findViewById(R.id.pressureValue);
        textView.setText(weatherInfo.getPressure() + getSpace() + weatherInfo.getPressureUnit());
        textView = findViewById(R.id.humidityValue);
        textView.setText(weatherInfo.getHumidity() + getSpace() + weatherInfo.getHumidityUnit());
        textView = findViewById(R.id.precipitationValue);
        textView.setText(weatherInfo.getPrecipitation());
        Button button = findViewById(R.id.sendToFriendButton);
        button.setOnClickListener(sendFriendClickListener);
        button = findViewById(R.id.lookWeatherInInternet);
        button.setOnClickListener(lookWeatherInInternetListener);

    }

    private WSimpleView getWeatherData() {
        return (WSimpleView) getIntent().getSerializableExtra(WConstants.WEATHER);
    }


}
