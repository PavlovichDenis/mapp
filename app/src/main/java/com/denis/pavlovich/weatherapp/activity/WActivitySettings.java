package com.denis.pavlovich.weatherapp.activity;


import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;

import com.denis.pavlovich.weatherapp.R;
import com.denis.pavlovich.weatherapp.utils.WConstants;

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

    private void setSelectedTheme(int theme) {
        setTheme(theme);
        recreate();
        savePreferences(WConstants.APP_THEME, theme);
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
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

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
