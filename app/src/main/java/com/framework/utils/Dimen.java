package com.framework.utils;

import android.util.DisplayMetrics;
import android.util.TypedValue;

import com.framework.app.MainApplication;


/**
 * Created by ran.feng flight_on 2015/3/6.
 * Dimensions的缩写
 */
public class Dimen {
    public static final int SCREEN_WIDTH;
    public static final int SCREEN_HEIGHT;
    static{
        DisplayMetrics dm = MainApplication.applicationContext.getResources().getDisplayMetrics();
        SCREEN_WIDTH = dm.widthPixels;
        SCREEN_HEIGHT = dm.heightPixels;
    }
    /**
     * dp -> px
     */
    public static float dpToPx(float dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, MainApplication.applicationContext.getResources()
                .getDisplayMetrics());
    }

    public static int dpToPx(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, MainApplication.applicationContext.getResources()
                .getDisplayMetrics());
    }
    /**
     * 以iOS为标准(640px宽)的效果图上标注的像素值,通过此方法可直接完全等比(横向)应用到当前手机.
     * 比如一根线在效果图上标为320px,刚好占屏幕一半宽,那调用iPxToPx(320)也刚好在当前手机上占屏幕一半宽
     */
    public static float iPhone640(float iPX) {
        return iPxToPx(iPX, 640f);
    }

    //跟上面一样,对应于750宽的效果图
    public static float iPhone750(float iPX) {
        return iPxToPx(iPX,750f);
    }

    public static int iPhone750Int(float iPX) {
        return fi(iPhone750(iPX));
    }

    public static float iPxToPx(float iPX,float designScreenWidth) {
        return SCREEN_WIDTH * iPX / designScreenWidth ;
    }

    public static float pxToDp(float px) {
        final float scale = MainApplication.applicationContext.getResources().getDisplayMetrics().density;
        return px / scale;
    }

    /**
     * 像素 float 转 int ; (像素需向上取整)
     */
    public static int fi(float px){
        return (int) (px+0.5f);
    }
}
