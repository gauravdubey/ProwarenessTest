package com.android.prowarenesstestapp;

import android.app.Application;

import com.android.prowarenesstestapp.prefs.SharedPrefUtils;

public class ProwarenessApplication extends Application{

    private static ProwarenessApplication _instance;
    private boolean closed;

    public ProwarenessApplication() {
        _instance = this;
        closed = false;

    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public static ProwarenessApplication getInstance() {
        if (_instance == null) {
            _instance = new ProwarenessApplication();
        }
        return _instance;
    }
    public SharedPrefUtils getSharedPrefUtils() {
        return SharedPrefUtils.getInstance(this);
    }

}
