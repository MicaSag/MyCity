package com.lastproject.mycity;

import android.app.Application;

import com.jakewharton.threetenabp.AndroidThreeTen;

public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Initialize ThreeTen Android Backport
        AndroidThreeTen.init(this);
    }
}
