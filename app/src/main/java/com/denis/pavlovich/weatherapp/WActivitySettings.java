package com.denis.pavlovich.weatherapp;


import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

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
    }

    private void setRadioListener(int button) {
        RadioButton rb = findViewById(button);
        rb.setOnClickListener(radioButtonClickListener);
    }


}
