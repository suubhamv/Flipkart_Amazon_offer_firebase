package com.ssvmakers.amzonew.Services;

import android.app.Application;

import com.onesignal.OneSignal;

/**
 * Created by Shubham on 11/21/2017.
 */

public class OneSignalService extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        OneSignal.startInit(this).init();
    }
}
