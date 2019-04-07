package com.denis.pavlovich.weatherapp.activity;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;

import com.denis.pavlovich.weatherapp.activity.fragment.WeatherDetailsFragment;

import static android.content.res.Configuration.ORIENTATION_LANDSCAPE;

public class WActivityInfo extends WAbstractActivityWithThemeSupport {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setTheme(getApplicationTheme());
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        if (getResources().getConfiguration().orientation == ORIENTATION_LANDSCAPE) {
            finish();
            return;
        }
        if (savedInstanceState == null) {
            WeatherDetailsFragment details = WeatherDetailsFragment.init(null);
            Bundle bundle = getIntent().getExtras();
            details.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().add(android.R.id.content, details).commit();
        }
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
