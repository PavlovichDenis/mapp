package com.denis.pavlovich.weatherapp.data;

import java.io.Serializable;

public class WData implements Serializable {

    //показывать ветер
    private boolean showWind;

    //показывать влажность
    private boolean showHumidity;

    //показывать давление
    private boolean showPressure;

    private int selectedIndex;

    public WData(boolean showWind, boolean showHumidity, boolean showPressure, int selectedIndex) {
        this.showWind = showWind;
        this.showHumidity = showHumidity;
        this.showPressure = showPressure;
        this.selectedIndex = selectedIndex;
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
}
