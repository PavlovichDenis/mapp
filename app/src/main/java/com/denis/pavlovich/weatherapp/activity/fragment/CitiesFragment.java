package com.denis.pavlovich.weatherapp.activity.fragment;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
import android.widget.ProgressBar;
import android.widget.Switch;

import com.denis.pavlovich.weatherapp.R;
import com.denis.pavlovich.weatherapp.activity.WActivityInfo;
import com.denis.pavlovich.weatherapp.entities.City;
import com.denis.pavlovich.weatherapp.services.CityService;
import com.denis.pavlovich.weatherapp.services.ParcelableObjectList;
import com.denis.pavlovich.weatherapp.utils.WConstants;
import com.denis.pavlovich.weatherapp.data.WData;

import java.util.List;

import static android.content.res.Configuration.ORIENTATION_LANDSCAPE;

public class CitiesFragment extends Fragment {

    private boolean existsDetails;

    private WData simpleView;

    private ProgressBar progressBar;

    final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            List<City> citiesList = null;
            try {
                Bundle bundle = intent.getExtras();
                if (bundle != null) {
                    ParcelableObjectList<City> objectList = bundle.getParcelable(WConstants.CITIES_LIST);
                    if (objectList != null) {
                        citiesList = objectList.getObjectList();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            configureRecycleView(getView(), citiesList);
            progressBar.setVisibility(View.INVISIBLE);
        }
    };

    interface OnItemClickListener {
        void itemClicked(City city);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    private OnItemClickListener onClickListener = new OnItemClickListener() {

        @Override
        public void itemClicked(City city) {
            simpleView = constructSimpleView(city);
            showWeather(simpleView);
        }
    };

    private Switch.OnCheckedChangeListener switchOnCheckedChangeListener = new Switch.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            simpleView = constructSimpleView(simpleView.getCity());
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

    private WData constructSimpleView(City city) {
        return new WData(
                getSwitchChecked(R.id.wind),
                getSwitchChecked(R.id.humidity),
                getSwitchChecked(R.id.pressure),
                city);
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
        progressBar = view.findViewById(R.id.cityProgressBar);
        //configureRecycleView(view);
        getCitiesList();
    }

    private void getCitiesList() {
        Activity activity = getActivity();
        if (activity != null) {
            progressBar.setVisibility(View.VISIBLE);
            Intent intent = new Intent(activity, CityService.class);
            activity.startService(intent);
        }
    }

    private void configureRecycleView(@Nullable View view, List<City> citiesList) {
        if (view == null || citiesList == null) {
            return;
        }
        RecyclerView recyclerView = view.findViewById(R.id.cities);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        RecyclerListAdapter adapter = new RecyclerListAdapter(citiesList, onClickListener);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        existsDetails = getView(R.id.fragment_params) != null;
        if (savedInstanceState != null) {
            simpleView = savedInstanceState.getParcelable(WConstants.WEATHER);
        }
        if (existsDetails) {
            showWeather(simpleView);
        }
    }

    private void registerCitiesReceiver() {
        // регистрируем BroadcastReceiver
        IntentFilter intentFilter = new IntentFilter(
                WConstants.SERVICE_CITY_RESPONSE);
        intentFilter.addCategory(Intent.CATEGORY_DEFAULT);
        Activity activity = getActivity();
        if (activity != null) {
            activity.registerReceiver(broadcastReceiver, intentFilter);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        Activity activity = getActivity();
        if (activity != null) {
            getActivity().unregisterReceiver(broadcastReceiver);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        registerCitiesReceiver();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(WConstants.WEATHER, simpleView);
    }

    private void showWeather(WData sv) {
        if (sv == null) {
            return;
        }
        if (existsDetails) {
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
