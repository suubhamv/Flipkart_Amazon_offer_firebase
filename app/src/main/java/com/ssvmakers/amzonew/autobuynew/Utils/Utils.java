package com.ssvmakers.amzonew.autobuynew.Utils;

import android.content.Context;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Shubham on 11/5/2017.
 */

public class Utils {
    private static FirebaseDatabase mDatabase;
    public static final String FlipUrl = "https://affiliate-api.flipkart.net/affiliate/search/json?";
    public static String APP_PREF = "amzo";
    public static final String isRated = "isRated";

    public static FirebaseDatabase getDataBase(Context context) {
        if (mDatabase == null) {

            if (FirebaseApp.getApps(context).isEmpty()) {
                FirebaseApp.initializeApp(context);
                FirebaseDatabase.getInstance().setPersistenceEnabled(true);
            }
            mDatabase = FirebaseDatabase.getInstance();

        } else {

        }
        return mDatabase;
    }
}
