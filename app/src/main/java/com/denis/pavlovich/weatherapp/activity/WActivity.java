package com.denis.pavlovich.weatherapp.activity;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.denis.pavlovich.weatherapp.R;
import com.denis.pavlovich.weatherapp.utils.WConstants;
import com.denis.pavlovich.weatherapp.view.UnitTextView;

public class WActivity extends WAbstractActivityWithThemeSupport {

    private SensorManager sensorManager;

    private Sensor sensorTemperature;

    private Sensor sensorHumidity;

    private UnitTextView tempView;

    private UnitTextView humView;

    private final NavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            new NavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    onOptionsItemSelected(menuItem);
                    DrawerLayout drawer = findViewById(R.id.drawer_layout);
                    drawer.closeDrawer(GravityCompat.START);
                    return true;
                }
            };

    SensorEventListener listenerTemp = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            tempView.setValue(Float.toString(event.values[0]));
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    };

    SensorEventListener listenerHum = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            humView.setValue(Float.toString(event.values[0]));
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(getApplicationTheme());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //toolbar здесь определяется, чтобы использовать его в двух последующих вызовах методов
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        configureDrawerLayout(toolbar);
        configureContextMenu();
        configureSensors();
    }

    private void configureContextMenu() {
        View view = findViewById(R.id.wetherText);
        registerForContextMenu(view);
    }

    private void configureDrawerLayout(Toolbar toolbar) {
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(navigationItemSelectedListener);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    private void configureSensors() {
        tempView = findViewById(R.id.currTemperature);
        humView = findViewById(R.id.currHumidity);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        if (sensorManager != null) {
            sensorHumidity = sensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY);
            sensorTemperature = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        }
        hideTextViewIfNeed(sensorTemperature, tempView);
        hideTextViewIfNeed(sensorHumidity, humView);
    }

    private void hideTextViewIfNeed(Sensor sensor, View view) {
        if (sensor == null) {
            view.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(listenerTemp, sensorTemperature);
        sensorManager.unregisterListener(listenerHum, sensorHumidity);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(listenerTemp, sensorTemperature, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(listenerHum, sensorHumidity, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        return onOptionsItemSelected(item);
    }

    private void showAbout() {
        Intent intent = new Intent(WActivity.this, WActivityAbout.class);
        startActivity(intent);
    }

    private void showSettings() {
        Intent intent = new Intent(WActivity.this, WActivitySettings.class);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.actionAbout:
                showAbout();
                break;
            case R.id.actionTheme:
                showSettings();
                break;
            case android.R.id.home:
                onBackPressed();
                break;
            default:
                return super.onOptionsItemSelected(item);

        }
        return true;
    }

    @Override
    protected int getApplicationTheme() {
        int theme = getIntPreferences(WConstants.APP_THEME);
        if (theme == 0) {
            theme = R.style.LightTheme_NoActionBar;
        } else if (theme == R.style.LightTheme) {
            theme = R.style.LightTheme_NoActionBar;
        } else {
            theme = R.style.DarkTheme_NoActionBar;
        }
        return theme;
    }
}
