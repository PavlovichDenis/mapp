package com.denis.pavlovich.weatherapp;

import android.support.v7.app.AppCompatActivity;

import com.denis.pavlovich.weatherapp.utils.WLogging;

public abstract class WAbstractActivity extends AppCompatActivity {

    @Override
    protected void onStart() {
        super.onStart();
        WLogging.complexToast(getApplicationContext(), this.getClass() + " onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        WLogging.complexToast(getApplicationContext(), this.getClass() + " onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        WLogging.complexToast(getApplicationContext(), this.getClass() + " onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        WLogging.complexToast(getApplicationContext(),this.getClass() + " onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        WLogging.complexToast(getApplicationContext(), this.getClass() + " onDestroy");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        WLogging.complexToast(getApplicationContext(), this.getClass() + " onRestart");
    }

}
