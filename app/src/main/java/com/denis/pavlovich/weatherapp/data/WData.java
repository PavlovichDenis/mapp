package com.denis.pavlovich.weatherapp.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.denis.pavlovich.weatherapp.entities.City;

public class WData implements Parcelable {

    //показывать ветер
    private boolean showWind;

    //показывать влажность
    private boolean showHumidity;

    //показывать давление
    private boolean showPressure;

    private City city;

    public WData(boolean showWind, boolean showHumidity, boolean showPressure, City city) {
        this.showWind = showWind;
        this.showHumidity = showHumidity;
        this.showPressure = showPressure;
        this.city = city;
    }

    private WData(Parcel in) {
        boolean[] data = new boolean[3];
        in.readBooleanArray(data);
        showHumidity = data[0];
        showPressure = data[1];
        showWind = data[2];
        city = in.readParcelable(WData.class.getClassLoader());
    }

    public boolean isShowWind() {
        return showWind;
    }

    public boolean isShowHumidity() {
        return showHumidity;
    }

    public boolean isShowPressure() {
        return showPressure;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeBooleanArray(new boolean[]{showHumidity, showPressure, showWind});
        dest.writeParcelable(city, flags);
    }

    public static final Parcelable.Creator<WData> CREATOR = new Parcelable.Creator<WData>() {

        @Override
        public WData createFromParcel(Parcel source) {
            return new WData(source);
        }

        @Override
        public WData[] newArray(int size) {
            return new WData[size];
        }
    };

}
