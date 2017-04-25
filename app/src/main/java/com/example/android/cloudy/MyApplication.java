package com.example.android.cloudy;

import android.app.Application;

import net.danlew.android.joda.JodaTimeAndroid;

/**
 * Created by ccu17 on 25/04/2017.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        JodaTimeAndroid.init(this);
    }

}
