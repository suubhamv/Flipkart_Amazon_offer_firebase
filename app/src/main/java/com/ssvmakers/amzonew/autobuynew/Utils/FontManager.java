package com.ssvmakers.amzonew.autobuynew.Utils;

import android.content.Context;
import android.graphics.Typeface;

/**
 * Created by Shubham on 11/5/2017.
 */

public class FontManager {
    public static Typeface getRaleway(Context context) {
        return Typeface.createFromAsset(context.getAssets(), "fonts/Raleway-Light.ttf");
    }

    public static Typeface getReckoner(Context context) {
        return Typeface.createFromAsset(context.getAssets(), "fonts/Reckoner_Bold.ttf");
    }

    public static Typeface getAwesome(Context context) {
        return Typeface.createFromAsset(context.getAssets(), "fonts/fontawesome-webfont.ttf");
    }
}
