package com.denis.pavlovich.weatherapp;


import com.denis.pavlovich.weatherapp.utils.WConstants;

public abstract class WAbstractActivityWithThemeSupport extends WAbstractActivity {

    private int oldTheme = 0;

    @Override
    protected void onResume() {
        super.onResume();
        int newTheme = this.getApplicationTheme();
        if (oldTheme != newTheme) {
            recreate();
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
}
