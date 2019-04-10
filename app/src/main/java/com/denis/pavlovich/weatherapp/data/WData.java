package com.denis.pavlovich.weatherapp.data;

import android.os.Parcel;
import android.os.Parcelable;

public class WData implements Parcelable {

    //показывать ветер
    private boolean showWind;

    //показывать влажность
    private boolean showHumidity;

    //показывать давление
    private boolean showPressure;

    private int selectedIndex;

    private String cityName;

    public WData(boolean showWind, boolean showHumidity, boolean showPressure, int selectedIndex, String cityName) {
        this.showWind = showWind;
        this.showHumidity = showHumidity;
        this.showPressure = showPressure;
        this.selectedIndex = selectedIndex;
        this.cityName = cityName;
    }

    private WData(Parcel in) {
        boolean[] data = new boolean[3];
        in.readBooleanArray(data);
        showHumidity = data[0];
        showPressure = data[1];
        showWind = data[2];
        selectedIndex = in.readInt();
        cityName = in.readString();
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

    public int getSelectedIndex() {
        return selectedIndex;
    }

    public String getCityName() {
        return cityName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeBooleanArray(new boolean[]{showHumidity, showPressure, showWind});
        dest.writeInt(selectedIndex);
        dest.writeString(cityName);
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
