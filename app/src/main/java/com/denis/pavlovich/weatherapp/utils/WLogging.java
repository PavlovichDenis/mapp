package com.denis.pavlovich.weatherapp.utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public final class WLogging {

    private static final String TAG = WConstants.TAG;

    public static void makeToast(Context context, String toast) {
        Toast.makeText(context, toast, Toast.LENGTH_SHORT).show();
    }

    public static void logToast(String message) {
        Log.d(TAG, message);
    }

    public static void complexToast(Context context, String toast) {
        makeToast(context, toast);
        logToast(toast);
    }

    private WLogging() {
    }
}
