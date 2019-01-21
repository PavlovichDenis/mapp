package com.denis.pavlovich.weatherapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.denis.pavlovich.weatherapp.utils.WConstants;


public class WActivityInfo extends WAbstractActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_winfo);
        TextView textView = findViewById(R.id.weather_view);
        textView.setText(getIntent().getStringExtra(WConstants.WEATHER));
    }


}
