package com.bz.poverty;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by chenxi.cui on 2017/9/30.
 */

public class WeatherLayout extends FrameLayout {
    private TextView tvCity;
    private TextView tvWeather;
    private TextView tvTemp;
    private ImageView imageWearher;

    public WeatherLayout(Context context) {
        super(context);
        initView();
    }

    public WeatherLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        LinearLayout.inflate(getContext(), R.layout.weather_layout, this);
        tvCity = (TextView) findViewById(R.id.tv_city);
        tvWeather = (TextView) findViewById(R.id.tv_weather);
        tvTemp = (TextView) findViewById(R.id.tv_temp);
        imageWearher = (ImageView) findViewById(R.id.image_weather);
    }

//    public void setData(List<WeatherResult.WeatherItem> weatherItems) {
//        this.mWeatherItems = weatherItems;
//        weatherAdapter.notifyDataSetChanged();
//    }

    public void setData(WeatherResult.WeatherData data) {
        tvCity.setText("实时天气 :   " + data.location.name);
        tvTemp.setText(data.now.temperature + "℃");
        tvWeather.setText(data.now.text);
        imageWearher.setImageBitmap(getImageFromAssetsFile("weather/" + data.now.code + ".png"));
    }

//    bgimg0 = getImageFromAssetsFile("Cat_Blink/cat_blink0000.png");

    private Bitmap getImageFromAssetsFile(String fileName) {
        Bitmap image = null;
        AssetManager am = getResources().getAssets();
        try {
            InputStream is = am.open(fileName);
            image = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return image;

    }

}
