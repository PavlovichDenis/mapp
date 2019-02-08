package com.denis.pavlovich.weatherapp.activity.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;

import com.denis.pavlovich.weatherapp.R;
import com.denis.pavlovich.weatherapp.activity.WActivityInfo;
import com.denis.pavlovich.weatherapp.dao.WDataProvider;
import com.denis.pavlovich.weatherapp.dao.WResourceDataProviderImpl;
import com.denis.pavlovich.weatherapp.entities.WeatherInfo;
import com.denis.pavlovich.weatherapp.utils.WConstants;
import com.denis.pavlovich.weatherapp.view.WSimpleView;

import java.util.ArrayList;
import java.util.List;

public class CitiesFragment extends Fragment {

    private boolean existsDetails;
    private List<WeatherInfo> weatherInfos;
    private WSimpleView simpleView;


    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        WDataProvider dataProvider = new WResourceDataProviderImpl(getResources());
        weatherInfos = dataProvider.getWeatherData();
        return view;
    }

    private List<String> getList() {
        ArrayList<String> list = new ArrayList<>();
        for (WeatherInfo wi : weatherInfos) {
            list.add(wi.getCity());
        }
        return list;
    }


    AdapterView.OnItemClickListener onClickListener = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            simpleView = constructSimpleView(position);
            showWeather(simpleView);
        }
    };

    Switch.OnCheckedChangeListener switchOnCheckedChangeListener = new Switch.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            simpleView = constructSimpleView(simpleView.getSelectedIndex());
            showWeather(simpleView);
        }
    };


    private WSimpleView constructSimpleView(int position) {
        WeatherInfo wi = weatherInfos.get(position);
        return new WSimpleView(
                ((Switch) getActivity().findViewById(R.id.wind)).isChecked(),
                ((Switch) getActivity().findViewById(R.id.humidity)).isChecked(),
                ((Switch) getActivity().findViewById(R.id.pressure)).isChecked(),
                wi,
                position);

    }

    private ListView getListView() {
        return getActivity().findViewById(R.id.cities);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ListView listView = getListView();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_list_item_activated_1, getList());
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(onClickListener);
        ((Switch) getActivity().findViewById(R.id.wind)).setOnCheckedChangeListener(switchOnCheckedChangeListener);
        ((Switch) getActivity().findViewById(R.id.humidity)).setOnCheckedChangeListener(switchOnCheckedChangeListener);
        ((Switch) getActivity().findViewById(R.id.pressure)).setOnCheckedChangeListener(switchOnCheckedChangeListener);
        existsDetails = getActivity().findViewById(R.id.fragment_params) != null;
        if (savedInstanceState != null) {
            // Restore last state for checked position.
            simpleView = (WSimpleView) savedInstanceState.getSerializable(WConstants.WEATHER);
        } else {
            simpleView = constructSimpleView(0);
        }
        if (existsDetails) {
            showWeather(simpleView);
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(WConstants.WEATHER, simpleView);
    }

    private void showWeather(WSimpleView sv) {
        if (existsDetails) {
            ListView listView = getListView();
            int selectedIndex = sv.getSelectedIndex();
            listView.setItemChecked(selectedIndex, true);
            listView.smoothScrollToPosition(selectedIndex);
            WetherDetailsFragment wd = WetherDetailsFragment.init(sv);
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.fragment_params, wd);
            ft.commit();
        } else {
            Intent intent = new Intent();
            intent.setClass(getActivity(), WActivityInfo.class);
            // и передадим туда параметры
            intent.putExtra(WConstants.WEATHER, sv);
            startActivity(intent);
        }
    }


}
