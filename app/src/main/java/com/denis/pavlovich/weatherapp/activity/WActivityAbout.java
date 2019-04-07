package com.denis.pavlovich.weatherapp.activity;


import android.os.Bundle;
import android.support.annotation.Nullable;

import com.denis.pavlovich.weatherapp.R;

public class WActivityAbout extends WAbstractActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
    }
}
