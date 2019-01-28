package com.denis.pavlovich.weatherapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Switch;

import com.denis.pavlovich.weatherapp.dao.WDataProvider;
import com.denis.pavlovich.weatherapp.dao.WResourceDataProviderImpl;
import com.denis.pavlovich.weatherapp.entities.WeatherInfo;
import com.denis.pavlovich.weatherapp.utils.WConstants;
import com.denis.pavlovich.weatherapp.view.WSimpleView;

import java.util.ArrayList;
import java.util.List;

public class WActivity extends WAbstractActivity {

    private List<WeatherInfo> weatherInfos;

    AdapterView.OnItemClickListener onClickListener = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            Intent intent = new Intent(WActivity.this, WActivityInfo.class);
            WeatherInfo wi = weatherInfos.get(position);
            WSimpleView simpleView = new WSimpleView(
                    ((Switch) findViewById(R.id.wind)).isChecked(),
                    ((Switch) findViewById(R.id.humidity)).isChecked(),
                    ((Switch) findViewById(R.id.pressure)).isChecked(),
                    wi);
            intent.putExtra(WConstants.WEATHER, simpleView);
            startActivity(intent);
        }
    };

    private List<String> getList() {
        ArrayList<String> list = new ArrayList<>();
        for (WeatherInfo wi : weatherInfos) {
            list.add(wi.getCity());
        }
        return list;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_w);
        WDataProvider dataProvider = new WResourceDataProviderImpl(getResources());
        weatherInfos = dataProvider.getWeatherData();
        ListView listView = findViewById(R.id.cities);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, getList());
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(onClickListener);

    }


}
