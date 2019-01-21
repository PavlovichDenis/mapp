package com.denis.pavlovich.weatherapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.denis.pavlovich.weatherapp.utils.WConstants;

public class WActivity extends WAbstractActivity {

    private final View.OnClickListener onClickListener = new View.OnClickListener() {

        private String getWeather(int id) {
            return getResources().getString(id);
        }


        @Override
        public void onClick(View view) {

            String weather = "";
            switch (view.getId()) {
                case R.id.to_weather_info_activity_m:
                    weather = getWeather(R.string.moscow_weather);
                    break;
                case R.id.to_weather_info_activity_n:
                    weather = getWeather(R.string.novosibrsk_weather);
                    break;
                case R.id.to_weather_info_activity_spb:
                    weather = getWeather(R.string.spb_weather);
                    break;
            }
            Intent intent = new Intent(WActivity.this, WActivityInfo.class);
            intent.putExtra(WConstants.WEATHER, weather);
            startActivity(intent);
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_w);
        Button button = findViewById(R.id.to_weather_info_activity_n);
        button.setOnClickListener(onClickListener);
        button = findViewById(R.id.to_weather_info_activity_m);
        button.setOnClickListener(onClickListener);
        button = findViewById(R.id.to_weather_info_activity_spb);
        button.setOnClickListener(onClickListener);
    }


}
