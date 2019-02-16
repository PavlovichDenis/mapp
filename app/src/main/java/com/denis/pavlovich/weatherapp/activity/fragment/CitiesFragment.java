package com.denis.pavlovich.weatherapp.activity.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.denis.pavlovich.weatherapp.R;
import com.denis.pavlovich.weatherapp.activity.WActivityInfo;
import com.denis.pavlovich.weatherapp.dao.WDataProvider;
import com.denis.pavlovich.weatherapp.dao.WResourceDataProviderImpl;
import com.denis.pavlovich.weatherapp.entities.WeatherInfo;
import com.denis.pavlovich.weatherapp.utils.WConstants;
import com.denis.pavlovich.weatherapp.data.WData;

import java.util.ArrayList;
import java.util.List;

import static android.content.res.Configuration.ORIENTATION_LANDSCAPE;

public class CitiesFragment extends Fragment {

    private boolean existsDetails;
    private List<WeatherInfo> weatherInfos;
    private WData simpleView;
    private RecyclerView recyclerView;

    interface OnItemClickListener {
        void itemClicked(int position);
    }

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


    private OnItemClickListener onClickListener = new OnItemClickListener() {

        @Override
        public void itemClicked(int position) {
            simpleView = constructSimpleView(position);
            showWeather(simpleView);
        }
    };

    private Switch.OnCheckedChangeListener switchOnCheckedChangeListener = new Switch.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            simpleView = constructSimpleView(simpleView.getSelectedIndex());
            if (getResources().getConfiguration().orientation == ORIENTATION_LANDSCAPE) {
                showWeather(simpleView);
            }
        }
    };

    private Switch getSwitch(int id) {
        return getActivity().findViewById(id);
    }

    private WData constructSimpleView(int position) {
        WeatherInfo wi = weatherInfos.get(position);
        return new WData(
                getSwitch(R.id.wind).isChecked(),
                getSwitch(R.id.humidity).isChecked(),
                getSwitch(R.id.pressure).isChecked(),
                wi,
                position);

    }

    private void setSwitchListener(View view, int id) {
        Switch sw = view.findViewById(id);
        sw.setOnCheckedChangeListener(switchOnCheckedChangeListener);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setSwitchListener(view, R.id.wind);
        setSwitchListener(view, R.id.humidity);
        setSwitchListener(view, R.id.pressure);

        recyclerView = view.findViewById(R.id.cities);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        RecyclerListAdapter adapter = new RecyclerListAdapter(getList(), onClickListener);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        existsDetails = getActivity().findViewById(R.id.fragment_params) != null;
        if (savedInstanceState != null) {
            simpleView = (WData) savedInstanceState.getSerializable(WConstants.WEATHER);
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

    private void showWeather(WData sv) {
        if (existsDetails) {
            int selectedIndex = sv.getSelectedIndex();
            if (recyclerView != null) {
                recyclerView.smoothScrollToPosition(selectedIndex);
            }
            WeatherDetailsFragment wd = WeatherDetailsFragment.init(sv);
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
