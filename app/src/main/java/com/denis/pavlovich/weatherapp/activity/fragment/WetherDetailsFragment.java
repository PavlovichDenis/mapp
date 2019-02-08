package com.denis.pavlovich.weatherapp.activity.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.denis.pavlovich.weatherapp.R;
import com.denis.pavlovich.weatherapp.entities.WeatherInfo;
import com.denis.pavlovich.weatherapp.utils.WConstants;
import com.denis.pavlovich.weatherapp.utils.WLogging;
import com.denis.pavlovich.weatherapp.view.WSimpleView;

public class WetherDetailsFragment extends Fragment {

    View.OnClickListener sendFriendClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent sendIntent = new Intent(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, getWeatherInfo().toString());
            sendIntent.setType(WConstants.TEXT_PLAIN);
            processIntent(sendIntent);
        }
    };


    View.OnClickListener lookWeatherInInternetListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            Uri uri = Uri.parse(getWeatherInfo().getWeatherInfo().getUrl());
            intent.setData(uri);
            processIntent(intent);

        }
    };


    private void processIntent(Intent intent) {
        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivity(intent);
        } else {
            WLogging.makeToast(getActivity().getBaseContext(), getResources().getString(R.string.noViewApp));
        }
    }

    public static WetherDetailsFragment init(WSimpleView simpleView) {
        WetherDetailsFragment fragment = new WetherDetailsFragment();    // создание
        Bundle args = new Bundle();
        args.putSerializable(WConstants.WEATHER, simpleView);
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
        WSimpleView simpleView = getWeatherInfo();
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
        Button button = layout.findViewById(R.id.sendToFriendButton);
        button.setOnClickListener(sendFriendClickListener);
        button = layout.findViewById(R.id.lookWeatherInInternet);
        button.setOnClickListener(lookWeatherInInternetListener);
        return layout;
    }

    public WSimpleView getWeatherInfo() {
        WSimpleView simpleView = (WSimpleView) getArguments().getSerializable(WConstants.WEATHER);
        return simpleView;
    }


}
