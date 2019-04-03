package com.denis.pavlovich.weatherapp.activity.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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
import com.denis.pavlovich.weatherapp.data.provider.IWDataProvider;
import com.denis.pavlovich.weatherapp.data.provider.WResourceDataProviderImpl;
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
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        IWDataProvider dataProvider = new WResourceDataProviderImpl(getResources());
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

    @Nullable
    private View getView(int id) {
        Activity activity = getActivity();
        if (activity != null) {
            return activity.findViewById(id);
        }
        return null;
    }

    private boolean getSwitchChecked(int id) {
        View view = getView(id);
        return view != null && ((Switch) view).isChecked();
    }

    private WData constructSimpleView(int position) {
        WeatherInfo wi = weatherInfos.get(position);
        return new WData(
                getSwitchChecked(R.id.wind),
                getSwitchChecked(R.id.humidity),
                getSwitchChecked(R.id.pressure),
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
        existsDetails = getView(R.id.fragment_params) != null;
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
    public void onSaveInstanceState(@NonNull Bundle outState) {
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
            FragmentManager fm = getFragmentManager();
            if (fm != null) {
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.fragment_params, wd);
                ft.commit();
            }
        } else {
            Activity activity = getActivity();
            if (activity != null) {
                Intent intent = new Intent();
                intent.setClass(activity, WActivityInfo.class);
                // и передадим туда параметры
                intent.putExtra(WConstants.WEATHER, sv);
                startActivity(intent);
            }
        }
    }


}
