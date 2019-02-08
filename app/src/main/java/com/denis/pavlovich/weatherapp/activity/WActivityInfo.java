package com.denis.pavlovich.weatherapp.activity;


import android.os.Bundle;
import android.support.annotation.Nullable;

import com.denis.pavlovich.weatherapp.activity.fragment.WetherDetailsFragment;

import static android.content.res.Configuration.ORIENTATION_LANDSCAPE;

public class WActivityInfo extends WAbstractActivityWithThemeSupport {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setTheme(getApplicationTheme());
        super.onCreate(savedInstanceState);
        if (getResources().getConfiguration().orientation == ORIENTATION_LANDSCAPE) {
            finish();
            return;
        }
        if (savedInstanceState == null) {
            WetherDetailsFragment details = new WetherDetailsFragment();
            details.setArguments(getIntent().getExtras());
            getSupportFragmentManager().beginTransaction().add(android.R.id.content, details).commit();
        }


    }

}
