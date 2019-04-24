package com.denis.pavlovich.weatherapp.activity;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;

import com.denis.pavlovich.weatherapp.utils.WConstants;

public abstract class WAbstractActivity extends AppCompatActivity {

    @Override
    protected void onStart() {
        super.onStart();
        //WLogging.complexToast(getApplicationContext(), getMessage("onStart"));
    }

    @Override
    protected void onResume() {
        super.onResume();
        //WLogging.complexToast(getApplicationContext(), getMessage("onResume"));
    }

    @Override
    protected void onPause() {
        super.onPause();
        //WLogging.complexToast(getApplicationContext(), getMessage("onPause"));
    }

    @Override
    protected void onStop() {
        super.onStop();
        //WLogging.complexToast(getApplicationContext(), getMessage("onStop"));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //WLogging.complexToast(getApplicationContext(), getMessage("onDestroy"));
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        //WLogging.complexToast(getApplicationContext(), getMessage("onRestart"));
    }

    protected void savePreferences(String key, int value) {
        SharedPreferences sharedPreferences = getSharedPreferences(
                WConstants.APP_PREFERENCES, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    protected int getIntPreferences(String key) {
        SharedPreferences sharedPreferences = getSharedPreferences(
                WConstants.APP_PREFERENCES, MODE_PRIVATE);
        return sharedPreferences.getInt(key, 0);
    }
    protected void savePreferences(String key, Long value) {
        SharedPreferences sharedPreferences = getSharedPreferences(
                WConstants.APP_PREFERENCES, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(key, value);
        editor.apply();
    }

    protected Long getLongPreferences(String key) {
        SharedPreferences sharedPreferences = getSharedPreferences(
                WConstants.APP_PREFERENCES, MODE_PRIVATE);
        return sharedPreferences.getLong(key, 0);
    }
}
