package com.bz.poverty;

import com.framework.domain.response.BaseResult;

import java.util.List;

/**
 * Created by chenxi.cui on 2017/9/30.
 */

public class WeatherResult extends BaseResult {
    public List<WeatherData> data;

    public static class WeatherData implements BaseData {
        public List<WeatherItem> weathers;
        public NowWeather now;
        public LocationWeather location;
    }

    public static class LocationWeather implements BaseData {
        public String timezone_offset;
        public String country;
        public String id;
        public String path;
        public String name;
        public String timezone;
    }

    public static class NowWeather implements BaseData {

        public String temperature;
        public String text;
        public String code;
    }

    public static class WeatherItem implements BaseData {
        public String date;
        public String temperature;
    }
}
