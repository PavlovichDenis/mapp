package com.denis.pavlovich.weatherapp;


import android.os.Bundle;

import com.denis.pavlovich.weatherapp.utils.WConstants;

public abstract class WAbstractActivityWithThemeSupport extends WAbstractActivity {

    private int oldTheme = 0;

    @Override
    protected void onResume() {
        super.onResume();
        int newTheme = this.getApplicationTheme();
        if (oldTheme != newTheme) {
            recreate();
            oldTheme = newTheme;
        }
    }

    @Override
    public void setTheme(int resId) {
        super.setTheme(resId);
        oldTheme = resId;
    }

    protected int getApplicationTheme() {
        int theme = getIntPreferences(WConstants.APP_THEME);
        if (theme == 0) {
            theme = R.style.LightTheme;
        }
        return theme;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(WConstants.ACTUAL_THEME, oldTheme);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        oldTheme = savedInstanceState.getInt(WConstants.ACTUAL_THEME, 0);
        super.onRestoreInstanceState(savedInstanceState);
    }
}
