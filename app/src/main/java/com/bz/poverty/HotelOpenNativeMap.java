package com.bz.poverty;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.widget.Toast;

import com.baidu.mapapi.model.LatLng;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenxi.cui on 2017/4/11.
 */

public class HotelOpenNativeMap {
    public static String[] paks = new String[]{"com.baidu.BaiduMap",        //百度
            "com.autonavi.minimap"};     //高德

    /**
     * 检查手机上是否安装了指定的软件
     *
     * @param context
     * @param packageName：应用包名
     * @return
     */
    public static boolean isAvilible(Context context, String packageName) {
        //获取packagemanager
        final PackageManager packageManager = context.getPackageManager();
        //获取所有已安装程序的包信息
        List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
        //用于存储所有已安装程序的包名
        List<String> packageNames = new ArrayList<String>();
        //从pinfo中将包名字逐一取出，压入pName list中
        if (packageInfos != null) {
            for (int i = 0; i < packageInfos.size(); i++) {
                String packName = packageInfos.get(i).packageName;
                packageNames.add(packName);
            }
        }
        //判断packageNames中是否有目标程序的包名，有TRUE，没有FALSE
        return packageNames.contains(packageName);
    }

    public static void startNative_Baidu(Context context, LatLng loc1, LatLng loc2, String startName, String endName, String appName) {
        try {
            Intent intent = Intent.getIntent("intent://map/direction?origin=latlng:" + loc1.latitude + "|name:" + startName + "&destination=latlng:" + loc2.latitude + "|name:" + endName + "&mode=driving&src=" + appName + "#Intent;scheme=bdapp;package=com.baidu.BaiduMap;end");
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "地址解析错误", Toast.LENGTH_SHORT).show();
        }
    }

    public static void startNative_Gaode(Context context, LatLng loc, String poiname, String appName) {
        if (loc == null) {
            return;
        }
        try {
            double[] doubles = GPSUtil.bd09_To_gps84(loc.latitude, loc.longitude);

            Intent intent = new Intent("android.intent.action.VIEW",
                    Uri.parse("androidamap://navi?sourceApplication=" + appName + "&poiname=" + poiname + "&lat=" + doubles[0] + "&lon=" + doubles[1] + "&dev=1&style=0"));
            intent.setPackage("com.autonavi.minimap");
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "地址解析错误", Toast.LENGTH_SHORT).show();
        }
    }
}
