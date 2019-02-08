package com.denis.pavlovich.weatherapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.denis.pavlovich.weatherapp.R;

public class WActivity extends WAbstractActivityWithThemeSupport {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(getApplicationTheme());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_w);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;

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
        }
        return true;
    }

}
