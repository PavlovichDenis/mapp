package com.denis.pavlovich.weatherapp.activity.fragment;

import android.app.Activity;
import android.content.Intent;
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
import android.widget.TextView;

import com.denis.pavlovich.weatherapp.R;
import com.denis.pavlovich.weatherapp.entities.WeatherInfo;
import com.denis.pavlovich.weatherapp.utils.WConstants;
import com.denis.pavlovich.weatherapp.utils.WLogging;
import com.denis.pavlovich.weatherapp.data.WData;

public class WeatherDetailsFragment extends Fragment {

    private View.OnClickListener lookWeatherInInternetListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            WData wData = getWeatherInfo();
            if (wData != null) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                Uri uri = Uri.parse(wData.getWeatherInfo().getUrl());
                intent.setData(uri);
                processIntent(intent);
            }
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
            args.putSerializable(WConstants.WEATHER, simpleView);
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
        TextView textView = layout.findViewById(R.id.cityName);
        WData simpleView = getWeatherInfo();
        if (simpleView != null) {
            WeatherInfo weatherInfo = simpleView.getWeatherInfo();
            textView.setText(weatherInfo.getCity());
            // устанавлиаем идимость элементов
            setVisible(layout.findViewById(R.id.windView), simpleView.isShowWind());
            setVisible(layout.findViewById(R.id.pressureView), simpleView.isShowPressure());
            setVisible(layout.findViewById(R.id.humidityView), simpleView.isShowHumidity());

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
        } else {
            // что-то пошло совсем не так
            WLogging.complexToast(getContext(), getString(R.string.emptyWData));
        }
        Button button = layout.findViewById(R.id.lookWeatherInInternet);
        button.setOnClickListener(lookWeatherInInternetListener);
        return layout;
    }

    @Nullable
    public WData getWeatherInfo() {
        if (getArguments() != null) {
            return (WData) getArguments().getSerializable(WConstants.WEATHER);
        }
        return null;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_info, menu);
        MenuItem item = menu.findItem(R.id.action_share);
        ShareActionProvider actionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(item);
        Intent sendIntent = createIntent();
        actionProvider.setShareIntent(sendIntent);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @NonNull
    private Intent createIntent() {
        WData simpleView = getWeatherInfo();
        Intent sendIntent = new Intent();
        if (simpleView != null) {
            sendIntent.putExtra(Intent.EXTRA_TEXT, simpleView.getViewText(getResources()));
        }
        sendIntent.setType(WConstants.TEXT_PLAIN);
        return sendIntent;
    }

}
