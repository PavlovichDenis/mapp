package com.denis.pavlovich.weatherapp.activity;


import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.Spinner;

import com.denis.pavlovich.weatherapp.R;
import com.denis.pavlovich.weatherapp.data.database.repository.CityRepository;
import com.denis.pavlovich.weatherapp.entities.City;
import com.denis.pavlovich.weatherapp.utils.WConstants;

import java.util.List;

public class WActivitySettings extends WAbstractActivityWithThemeSupport {

    View.OnClickListener radioButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            RadioButton rb = (RadioButton) v;
            switch (rb.getId()) {
                case R.id.lightTheme:
                    setSelectedTheme(R.style.LightTheme);
                    break;
                case R.id.darkTheme:
                    setSelectedTheme(R.style.DarkTheme);
                    break;
            }

        }
    };

    AdapterView.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            City selectedCity = (City) parent.getItemAtPosition(position);
            Long cityId = selectedCity.getId();
            savePreferences(WConstants.FAVORITE_CITY_ID, cityId);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    };

    private void setSelectedTheme(int theme) {
        setTheme(theme);
        savePreferences(WConstants.APP_THEME, theme);
        recreate();
    }

    private void setCheckedButton(int theme) {
        int rbId;
        if (theme == R.style.LightTheme) {
            rbId = R.id.lightTheme;
        } else {
            rbId = R.id.darkTheme;
        }
        ((RadioButton) findViewById(rbId)).setChecked(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        int theme = getApplicationTheme();
        setTheme(theme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wsettings);
        setCheckedButton(theme);
        setRadioListener(R.id.lightTheme);
        setRadioListener(R.id.darkTheme);
        configureActionBar();
        configureSpinner();
    }

    private void configureSpinner() {
        Spinner spinner = findViewById(R.id.spinnerCityList);
        List<City> citiesList = CityRepository.getInstance().getAllList();
        ArrayAdapter<City> adapter = new ArrayAdapter<>(this,
                R.layout.spinner_item, citiesList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(itemSelectedListener);
        spinner.setSelected(false);
        setSelectedCity(spinner, citiesList);
    }

    private void setSelectedCity(Spinner spinner, List<City> citiesList) {
        Long cityId = getLongPreferences(WConstants.FAVORITE_CITY_ID);
        if (cityId != null && cityId != 0) {
            for (int i = 0; i < citiesList.size(); i++) {
                City city = citiesList.get(i);
                if (city.getId().equals(cityId)) {
                    spinner.setSelection(i);
                    break;
                }
            }
        }
    }

    private void configureActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void setRadioListener(int button) {
        RadioButton rb = findViewById(button);
        rb.setOnClickListener(radioButtonClickListener);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
