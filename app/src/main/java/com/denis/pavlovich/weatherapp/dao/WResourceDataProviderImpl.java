package com.denis.pavlovich.weatherapp.dao;

import android.content.res.Resources;
import android.support.annotation.NonNull;

import com.denis.pavlovich.weatherapp.R;
import com.denis.pavlovich.weatherapp.entities.WeatherInfo;
import com.denis.pavlovich.weatherapp.utils.WLogging;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/*
 *Данные о погоде берем из xml файла в ресурсах
 *
 */

interface AttributeProcessor {
    void processAttribute(XmlPullParser parser, WeatherInfo weatherInfo, int index);
}

public class WResourceDataProviderImpl implements WDataProvider {

    private List<WeatherInfo> weatherInfos;
    private Resources resources;

    private AttributeProcessor windAttributeProcessor = new AttributeProcessor() {
        @Override
        public void processAttribute(XmlPullParser parser, WeatherInfo weatherInfo, int index) {
            String attrName = parser.getAttributeName(index);
            switch (attrName) {
                case "direction":
                    weatherInfo.setWindDirection(parser.getAttributeValue(index));
                    break;
                case "speed":
                    weatherInfo.setWindSpeed(parser.getAttributeValue(index));
                    break;
                case "unit":
                    weatherInfo.setWindSpeedUnit(parser.getAttributeValue(index));
                    break;
            }
        }
    };

    private AttributeProcessor tempAttributeProcessor = new AttributeProcessor() {
        @Override
        public void processAttribute(XmlPullParser parser, WeatherInfo weatherInfo, int index) {
            String attrName = parser.getAttributeName(index);
            if (attrName.equals("value")) {
                weatherInfo.setTemperature(parser.getAttributeValue(index));
            } else if (attrName.equals("unit")) {
                weatherInfo.setTemperatureUnit(parser.getAttributeValue(index));
            }
        }
    };

    private AttributeProcessor pressAttributeProcessor = new AttributeProcessor() {
        @Override
        public void processAttribute(XmlPullParser parser, WeatherInfo weatherInfo, int index) {
            String attrName = parser.getAttributeName(index);
            if (attrName.equals("value")) {
                weatherInfo.setPressure(parser.getAttributeValue(index));
            } else if (attrName.equals("unit")) {
                weatherInfo.setPressureUnit(parser.getAttributeValue(index));
            }
        }
    };

    private AttributeProcessor humAttributeProcessor = new AttributeProcessor() {
        @Override
        public void processAttribute(XmlPullParser parser, WeatherInfo weatherInfo, int index) {
            String attrName = parser.getAttributeName(index);
            if (attrName.equals("value")) {
                weatherInfo.setHumidity(parser.getAttributeValue(index));
            } else if (attrName.equals("unit")) {
                weatherInfo.setHumidityUnit(parser.getAttributeValue(index));
            }
        }
    };



    private void addDataToList(WeatherInfo weatherInfo) {
        if (weatherInfo != null) {
            weatherInfos.add(weatherInfo);
        }
    }


    private void processAttributes(XmlPullParser parser, @NonNull WeatherInfo weatherInfo, AttributeProcessor attributeProcessor) {
        for (int i = 0; i < parser.getAttributeCount(); i++) {
            attributeProcessor.processAttribute(parser, weatherInfo, i);
        }
    }

    private void parseXml(@NonNull XmlPullParser parser) throws XmlPullParserException, IOException {
        WeatherInfo weatherInfo = null;
        while (parser.getEventType() != XmlPullParser.END_DOCUMENT) {
            if (parser.getEventType() == XmlPullParser.START_TAG) {
                String tagName = parser.getName();
                switch (tagName) {
                    case "City":
                        addDataToList(weatherInfo);
                        weatherInfo = new WeatherInfo();
                        weatherInfo.setCity(parser.getAttributeValue(0));
                        weatherInfo.setUrl(parser.getAttributeValue(1));
                        break;
                    case "Wind":
                        processAttributes(parser, weatherInfo, windAttributeProcessor);
                        break;
                    case "Temperature":
                        processAttributes(parser, weatherInfo, tempAttributeProcessor);
                        break;
                    case "Precipitation":
                        weatherInfo.setPrecipitation(parser.getAttributeValue(0));
                        break;
                    case "Pressure":
                        processAttributes(parser, weatherInfo, pressAttributeProcessor);
                        break;
                    case "Humidity":
                        processAttributes(parser, weatherInfo, humAttributeProcessor);
                        break;
                }
            }
            parser.next();
        }
        addDataToList(weatherInfo);
    }

    public WResourceDataProviderImpl(@NonNull Resources resources) {
        this.resources = resources;
        weatherInfos = new ArrayList<>();

    }

    @Override
    public List<WeatherInfo> getWeatherData() {
        XmlPullParser parser = this.resources.getXml(R.xml.weatther_data);
        try {
            parseXml(parser);
        } catch (Exception e) {
            WLogging.logToast(e.getMessage());
        }
        return weatherInfos;
    }
}
