package com.denis.pavlovich.weatherapp;

import android.support.v7.app.AppCompatActivity;

import com.denis.pavlovich.weatherapp.utils.WLogging;

public abstract class WAbstractActivity extends AppCompatActivity {


    private String getMessage(String method) {
        return this.getClass().getSimpleName() + " " + method;
    }

    @Override
    protected void onStart() {
        super.onStart();
        WLogging.complexToast(getApplicationContext(), getMessage("onStart"));
    }

    @Override
    protected void onResume() {
        super.onResume();
        WLogging.complexToast(getApplicationContext(), getMessage("onResume"));
    }

    @Override
    protected void onPause() {
        super.onPause();
        WLogging.complexToast(getApplicationContext(), getMessage("onPause"));
    }

    @Override
    protected void onStop() {
        super.onStop();
        WLogging.complexToast(getApplicationContext(),getMessage("onStop"));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        WLogging.complexToast(getApplicationContext(), getMessage("onDestroy"));
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        WLogging.complexToast(getApplicationContext(), getMessage("onRestart"));
    }

}
