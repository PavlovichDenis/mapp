package com.denis.pavlovich.weatherapp.activity.fragment;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.ShareActionProvider;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.denis.pavlovich.weatherapp.R;
import com.denis.pavlovich.weatherapp.entities.WeatherInfo;
import com.denis.pavlovich.weatherapp.services.WeatherDetailsService;
import com.denis.pavlovich.weatherapp.utils.WConstants;
import com.denis.pavlovich.weatherapp.utils.WLogging;
import com.denis.pavlovich.weatherapp.data.WData;

public class WeatherDetailsFragment extends Fragment {

    private WData weatherDataFlags;

    private WeatherInfo weatherInfo;

    private ProgressBar progressBar;

    private ShareActionProvider actionProvider;

    private View.OnClickListener lookWeatherInInternetListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (weatherDataFlags != null && weatherInfo != null) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                Uri uri = Uri.parse(weatherInfo.getUrl());
                intent.setData(uri);
                processIntent(intent);
            }
        }
    };

    final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                Bundle bundle = intent.getExtras();
                if (bundle != null) {
                    weatherInfo = bundle.getParcelable(WConstants.CITY_WEATHER_DATA);
                    showWeatherData(getView());
                    Intent sendIntent = createIntent();
                    actionProvider.setShareIntent(sendIntent);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            progressBar.setVisibility(View.INVISIBLE);
        }
    };

    private void processIntent(Intent intent) {
        Activity activity = getActivity();
        if (activity != null) {
            if (intent.resolveActivity(activity.getPackageManager()) != null) {
                startActivity(intent);
            } else {
                WLogging.makeToast(getActivity().getBaseContext(), getResources().getString(R.string.noViewApp));
            }
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setHasOptionsMenu(true);
    }

    public static WeatherDetailsFragment init(WData simpleView) {
        WeatherDetailsFragment fragment = new WeatherDetailsFragment();    // создание
        Bundle args = new Bundle();
        if (simpleView != null) {
            args.putParcelable(WConstants.WEATHER, simpleView);
        }
        fragment.setArguments(args);
        return fragment;
    }

    private void setVisible(View view, boolean visible) {
        if (visible) {
            view.setVisibility(View.VISIBLE);
        } else {
            view.setVisibility(View.GONE);
        }
    }

    private String getSpace() {
        return getResources().getString(R.string.space);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_info, container, false);
        Button button = layout.findViewById(R.id.lookWeatherInInternet);
        button.setOnClickListener(lookWeatherInInternetListener);
        progressBar = layout.findViewById(R.id.weatherProgressBar);
        return layout;
    }

    private void getWeatherData() {
        Activity activity = getActivity();
        if (activity != null) {
            progressBar.setVisibility(View.VISIBLE);
            Intent intent = new Intent(activity, WeatherDetailsService.class);
            intent.putExtra(WConstants.CITY_SELECTED, weatherDataFlags.getSelectedIndex());
            intent.putExtra(WConstants.CITY_SELECTED_NAME, weatherDataFlags.getCityName());
            activity.startService(intent);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        weatherDataFlags = getWeatherDataFlags();
        if (weatherDataFlags != null) {
            getWeatherData();
        } else {
            // что-то пошло совсем не так
            WLogging.complexToast(getContext(), getString(R.string.emptyWData));
        }
    }

    private void registerWeatherReciver() {
        // регистрируем BroadcastReceiver
        IntentFilter intentFilter = new IntentFilter(
                WConstants.SERVICE_CITY_WEATHER_RESPONSE);
        intentFilter.addCategory(Intent.CATEGORY_DEFAULT);
        Activity activity = getActivity();
        if (activity != null) {
            activity.registerReceiver(broadcastReceiver, intentFilter);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        registerWeatherReciver();
    }

    @Override
    public void onPause() {
        super.onPause();
        Activity activity = getActivity();
        if (activity != null) {
            getActivity().unregisterReceiver(broadcastReceiver);
        }
    }

    private void showWeatherData(@Nullable View layout) {
        if (layout == null) {
            return;
        }
        if (weatherInfo == null) {
            return;
        }
        TextView textView = layout.findViewById(R.id.cityName);
        textView.setText(weatherInfo.getCity());
        // устанавлиаем идимость элементов
        setVisible(layout.findViewById(R.id.windView), weatherDataFlags.isShowWind());
        setVisible(layout.findViewById(R.id.pressureView), weatherDataFlags.isShowPressure());
        setVisible(layout.findViewById(R.id.humidityView), weatherDataFlags.isShowHumidity());

        //Устанавлиаем сведения о погоде
        textView = layout.findViewById(R.id.temperatureValue);
        textView.setText(String.format("%s%s%s", weatherInfo.getTemperature(), getSpace(), weatherInfo.getTemperatureUnit()));
        textView = layout.findViewById(R.id.windValue);
        textView.setText(String.format("%s%s%s%s%s", weatherInfo.getWindDirection(), getSpace(), weatherInfo.getWindSpeed(), getSpace(), weatherInfo.getWindSpeedUnit()));
        textView = layout.findViewById(R.id.pressureValue);
        textView.setText(String.format("%s%s%s", weatherInfo.getPressure(), getSpace(), weatherInfo.getPressureUnit()));
        textView = layout.findViewById(R.id.humidityValue);
        textView.setText(String.format("%s%s%s", weatherInfo.getHumidity(), getSpace(), weatherInfo.getHumidityUnit()));
        textView = layout.findViewById(R.id.precipitationValue);
        textView.setText(weatherInfo.getPrecipitation());
    }

    @Nullable
    public WData getWeatherDataFlags() {
        if (getArguments() != null) {
            return (WData) getArguments().getParcelable(WConstants.WEATHER);
        }
        return null;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_info, menu);
        MenuItem item = menu.findItem(R.id.action_share);
        actionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(item);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @NonNull
    private Intent createIntent() {
        Intent sendIntent = new Intent();
        if (weatherInfo != null) {
            sendIntent.putExtra(Intent.EXTRA_TEXT, weatherInfo.getViewText(getResources()));
        }
        sendIntent.setType(WConstants.TEXT_PLAIN);
        return sendIntent;
    }
}
