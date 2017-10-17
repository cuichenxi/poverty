package com.bz.poverty;

import android.graphics.drawable.Drawable;

/**
 * Created by chenxi.cui on 2017/4/11.
 */

public class AppInfo {
    private String appName;
    private String packageName;
    private String versionName;
    private int versionCode;
    private Drawable appIcon;

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public void setAppIcon(Drawable appIcon) {
        this.appIcon = appIcon;
    }
}
