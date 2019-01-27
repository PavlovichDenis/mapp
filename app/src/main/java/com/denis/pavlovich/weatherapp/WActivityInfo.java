package com.denis.pavlovich.weatherapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.denis.pavlovich.weatherapp.entities.WeatherInfo;
import com.denis.pavlovich.weatherapp.utils.WConstants;
import com.denis.pavlovich.weatherapp.view.WSimpleView;


public class WActivityInfo extends WAbstractActivity {

    private void setVisible(View view, boolean visible) {
        if (visible) {
            view.setVisibility(View.VISIBLE);
        } else {
            view.setVisibility(View.GONE);
        }
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_winfo);
        TextView textView = findViewById(R.id.cityName);
        WSimpleView simpleView = (WSimpleView) getIntent().getSerializableExtra(WConstants.WEATHER);
        WeatherInfo weatherInfo = simpleView.getWeatherInfo();
        textView.setText(weatherInfo.getCity());
        // устанавлиаем идимость элементов
        setVisible(findViewById(R.id.windView), simpleView.isShowWind());
        setVisible(findViewById(R.id.pressureView), simpleView.isShowPressure());
        setVisible(findViewById(R.id.humidityView), simpleView.isShowHumidity());

        //Устанавлиаем сведения о погоде
        textView = findViewById(R.id.temperatureValue);
        textView.setText(weatherInfo.getTemperature() + " " + weatherInfo.getTemperatureUnit());
        textView = findViewById(R.id.windValue);
        textView.setText(weatherInfo.getWindDirection() + " " + weatherInfo.getWindSpeed() + " " + weatherInfo.getWindSpeedUnit());
        textView = findViewById(R.id.pressureValue);
        textView.setText(weatherInfo.getPressure() + " " + weatherInfo.getPressureUnit());
        textView = findViewById(R.id.humidityValue);
        textView.setText(weatherInfo.getHumidity() + " " + weatherInfo.getHumidityUnit());
        textView = findViewById(R.id.precipitationValue);
        textView.setText(weatherInfo.getPrecipitation());

    }


}
